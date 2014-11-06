package pl.jahu.mpk.tools;

import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.StationData;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.utils.DownloadUtils;
import pl.jahu.mpk.utils.LineNumbersResolver;
import pl.jahu.mpk.utils.UrlResolver;

import javax.inject.Inject;
import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class TimetablesDownloader {

    public static final String TIMETABLES_LOCATION = "offline/timetables/";
    public static final String LINES_PAGE_NAME = "_lines.html";
    public static final String MENU_PAGE_NAME = "_menu.html";
    public static final String STATIONS_PAGE_NAME = "_stations.html";

    @Inject
    static TimetableProvider timetableProvider;

    @Inject
    static DownloadUtils downloadUtils;

    /**
     * Downloads:
     * - lines list page, showing which lines timetables has changed
     * - menu page, containing next timetable changes dates and links to new timetables
     */
    public static void downloadInfo() throws TimetableNotFoundException {
        downloadUtils.downloadUrl(UrlResolver.LINES_LIST_URL, TIMETABLES_LOCATION + LINES_PAGE_NAME);
        downloadUtils.downloadUrl(UrlResolver.TIMETABLE_MENU_URL, TIMETABLES_LOCATION + MENU_PAGE_NAME);
        downloadUtils.downloadUrl(UrlResolver.STATIONS_LIST_URL, TIMETABLES_LOCATION + STATIONS_PAGE_NAME);
    }

    public static void downloadTimetables(int firstLine, int lastLine) {
        downloadTimetables(new LineNumber(firstLine), new LineNumber(lastLine));
    }


    /**
     * Downloads timetables for lines from specified range [firstLine, lastLine] and saves them locally
     */
    public static void downloadTimetables(LineNumber firstLine, LineNumber lastLine) {

        try {
            List<LineNumber> lines = timetableProvider.getLinesList();

            int[] linesRange = LineNumbersResolver.getLinesFromRange(lines, firstLine, lastLine);
            for (int i = linesRange[0]; i <= linesRange[1]; i++) {
                LineNumber line = lines.get(i);
                for (int j = 1; j < 10; j++) {
                    try {
                        String lineRouteUrl = UrlResolver.getLineRouteUrl(line, j);
                        downloadUtils.downloadUrl(lineRouteUrl, TIMETABLES_LOCATION + getPageName(lineRouteUrl));

                        List<StationData> route = timetableProvider.getLineRoute(line, j);
                        for (StationData station : route) {
                            String url = UrlResolver.getStationTimetableUrl(line, station.getUrlLocation());
                            downloadUtils.downloadUrl(url, TIMETABLES_LOCATION + getPageName(url));
                        }
                    } catch (TimetableNotFoundException e) {
                        break;
                    }
                }
            }
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        } catch (LineRouteParseException e) {
            e.printStackTrace();
        }
    }





    private static String getPageName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
