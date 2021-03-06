package pl.jahu.mpk.tools;

import pl.jahu.mpk.AppModule;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
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

    static final String TIMETABLES_LOCATION = "offline/timetables/";
    private static final String LINES_PAGE_NAME = "_lines.html";
    private static final String MENU_PAGE_NAME = "_menu.html";
    private static final String STATIONS_PAGE_NAME = "_stations.html";
    private static final int DEFAULT_MIN_LINE_NUMBER = 0;
    private static final int DEFAULT_MAX_LINE_NUMBER = 1000;

    @Inject static TimetableProvider timetableProvider;
    @Inject static DownloadUtils downloadUtils;

    /**
     * Downloads:
     * - lines list page, showing which lines timetables has changed
     * - menu page, containing next timetable changes dates and links to new timetables
     */
    public static void downloadInfo() throws ParsableDataNotFoundException {
        downloadUtils.downloadUrl(UrlResolver.LINES_LIST_URL, TIMETABLES_LOCATION + LINES_PAGE_NAME);
        downloadUtils.downloadUrl(UrlResolver.UPDATE_INFO_URL, TIMETABLES_LOCATION + MENU_PAGE_NAME);
        downloadUtils.downloadUrl(UrlResolver.STATIONS_LIST_URL, TIMETABLES_LOCATION + STATIONS_PAGE_NAME);
    }

    public static void downloadTimetables(int firstLine, int lastLine) {
        downloadTimetables(new LineNumber(firstLine), new LineNumber(lastLine));
    }

    /**
     * Downloads timetables for lines from specified range [firstLine, lastLine] and saves them locally
     */
    public static void downloadTimetables(LineNumber firstLineNumber, LineNumber lastLineNumber) {
        try {
            List<Line> lines = timetableProvider.getLinesList();
            int[] linesRange = LineNumbersResolver.getLinesFromRange(lines, firstLineNumber, lastLineNumber);
            for (int i = linesRange[0]; i <= linesRange[1]; i++) {
                Line line = lines.get(i);
                int direction = 1;
                while (downloadLineRouteData(line, direction)) {
                    List<StationData> route = timetableProvider.getLineRoute(line, direction);
                    for (StationData station : route) {
                        String url = UrlResolver.getStationTimetableUrl(line.getNumber(), station.getSequenceNumber());
                        downloadUtils.downloadUrl(url, TIMETABLES_LOCATION + getPageName(url));
                    }
                    direction++;
                }
            }
        } catch (ParsableDataNotFoundException | TimetableParseException e) {
            e.printStackTrace();
        }
    }

    private static boolean downloadLineRouteData(Line line, int direction) {
        String lineRouteUrl = UrlResolver.getLineRouteUrl(line.getNumber(), direction);
        return downloadUtils.downloadUrl(lineRouteUrl, TIMETABLES_LOCATION + getPageName(lineRouteUrl));
    }

    private static String getPageName(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public static void main(String args[]) {
        int minLineNumber = DEFAULT_MIN_LINE_NUMBER;
        int maxLineNumber = DEFAULT_MAX_LINE_NUMBER;

        if (args.length == 2) {
            try {
                minLineNumber = Integer.parseInt(args[0]);
                maxLineNumber = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                minLineNumber = DEFAULT_MIN_LINE_NUMBER;
                maxLineNumber = DEFAULT_MAX_LINE_NUMBER;
            }
        }

        DaggerApplication.init(new AppModule());

        try {
            downloadInfo();
            downloadTimetables(minLineNumber, maxLineNumber);
        } catch (ParsableDataNotFoundException e) {
            e.printStackTrace();
        }

    }
}
