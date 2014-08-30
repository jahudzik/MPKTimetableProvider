package pl.jahu.mpk.tests.integration;

import org.junit.AfterClass;
import org.junit.Test;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.LineRouteParser;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by jahudzik on 2014-08-03.
 *
 * Based on Scenario1ShowLineTimetable
 */
public class ShowTimetableIntegrationTest {

    private static int actLine;
    private static String destination;
    private static String actTimetableUrl;
    private static boolean printOutput;

    @Test
    public void testShowingTimetable() {
        try {
            showTimetable(0, 1000, true);
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (LineRouteParseException e) {
            e.printStackTrace();
            fail();
        } catch (TimetableParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @AfterClass
    public static void logAfter() {
        if (printOutput) {
            System.out.println("LAST EXECUTED: {line=" + actLine + ", destination='" + destination + "', url=" + actTimetableUrl + "}");
        }
    }

    public static void showTimetable(int firstLine, int lastLine, boolean printOutput) throws TimetableNotFoundException, LineRouteParseException, TimetableParseException {
        ShowTimetableIntegrationTest.printOutput = printOutput;
        LinesListParser linesListParser = new LinesListParser();
        List<Integer> lines = linesListParser.parse();
        assertNotNull(lines);
        assertTrue(lines.size() > 0);

        Collections.sort(lines);

        int firstLineIndex = 0;
        while (lines.get(firstLineIndex) < firstLine) {
            firstLineIndex++;
        }

        int lastLineIndex = lines.size() - 1;
        while (lines.get(lastLineIndex) > lastLine) {
            lastLineIndex--;
        }

        // for each line...
        for (int k = firstLineIndex; k <= lastLineIndex; k++) {
            int line = lines.get(k);
            actLine = line;
            // for each destination...
            for (int i = 1; i < 10; i++) {
                try {
                    LineRouteParser routeParser = new LineRouteParser(line, i);
                    destination = routeParser.getDestination();
                    // get route (all stations)
                    List<String[]> route = routeParser.parse();
                    assertNotNull(route);
                    assertTrue(route.size() > 0);
                    for (String[] station : route) {
                        assertNotNull(station);
                        assertEquals(2, station.length);
                        assertNotNull(station[0]);
                        assertNotNull(station[1]);
                    }

                    // parse timetable for the first station on the route
                    TimetableParser timetableParser = new TimetableParser(line, route.get(0)[1]);
                    actTimetableUrl = timetableParser.getUrl();
                    Map<DayTypes, List<Departure>> timetables = timetableParser.parse();
                    assertNotNull(timetables);
                    assertTrue(timetables.size() > 0);

                    StringBuilder sb = new StringBuilder();
                    for (DayTypes dayType : timetables.keySet()) {
                        List<Departure> departures = timetables.get(dayType);
                        sb.append(dayType).append("=").append(departures.size()).append(";");
                        assertNotNull(departures);
                        assertTrue(departures.size() > 0);
                    }

                    if (printOutput) {
                        System.out.println("PASSED: {line=" + line + ", route='" + route.get(0)[0] + "'->'" + destination + "', stations=" + route.size() + ", departures={" + sb.toString() + "} }");
                    }
                } catch (TimetableNotFoundException e) {
                    break;
                }
            }
        }
    }

}
