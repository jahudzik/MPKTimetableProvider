package pl.jahu.mpk.samples;

import org.apache.commons.io.FileUtils;
import pl.jahu.mpk.parser.LineRouteParser;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.utils.LineNumbersResolver;
import pl.jahu.mpk.parser.utils.UrlResolver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class TimetablesDownloader {

    public static final String TIMETABLES_LOCATION = "offline/timetables/";


    /**
     * Downloads timetables for lines from specified range [firstLine, lastLine] and saves them locally
     */
    public static void download(int firstLine, int lastLine) {

        try {
            LinesListParser linesListParser = new LinesListParser();
            List<Integer> lines = linesListParser.parse();

            int[] linesRange = LineNumbersResolver.getLinesFromRange(lines, firstLine, lastLine);
            for (int i = linesRange[0]; i <= linesRange[1]; i++) {
                int line = lines.get(i);
                for (int j = 1; j < 10; j++) {
                    try {
                        LineRouteParser routeParser = new LineRouteParser(line, j);
                        List<String[]> route = routeParser.parse();
                        for (String[] station : route) {
                            String url = UrlResolver.getStationTimetableUrl(line, station[1]);
                            System.out.println(url);
                            FileUtils.copyURLToFile(new URL(url), new File(TIMETABLES_LOCATION + getPage(url)));
                        }
                    } catch (TimetableNotFoundException e) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        } catch (LineRouteParseException e) {
            e.printStackTrace();
        }
    }

    private static String getPage(String url) {
        return url.substring(url.lastIndexOf("/"));
    }

}
