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
import java.util.HashMap;
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
    private static String timetableUrl;

    @Test
    public void testShowingTimetable() {
        try {
            showTimetable();
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
        System.out.println("LAST EXECUTED: {line=" + actLine + ", destination='" + destination + "', url=" + timetableUrl + "}");
    }

    private void showTimetable() throws TimetableNotFoundException, LineRouteParseException, TimetableParseException {
        LinesListParser linesListParser = new LinesListParser();
        List<Integer> lines = linesListParser.parse();
        assertNotNull(lines);
        assertTrue(lines.size() > 0);

        Collections.sort(lines);

        // for each line...
        for (int line : lines) {
            actLine = line;
            // for each destination...
            for (int i = 1; i < 10; i++) {
                String url = LineRouteParser.getLineRouteUrl(line, i);
                try {
                    LineRouteParser routeParser = new LineRouteParser(url);
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
                    timetableUrl = TimetableParser.getStationTimetableUrl(line, route.get(0)[1]);
                    TimetableParser timetableParser = new TimetableParser(timetableUrl);
                    Map<DayTypes, List<Departure>> timetables = timetableParser.parse();
                    assertNotNull(timetables);
                    assertTrue(timetables.size() > 0);

                    StringBuffer sb = new StringBuffer();
                    for (DayTypes dayType : timetables.keySet()) {
                        List<Departure> departures = timetables.get(dayType);
                        sb.append(dayType + "=" + departures.size() + ";");
                        assertNotNull(departures);
                        assertTrue(departures.size() > 0);
                    }

                    System.out.println("PASSED: {line=" + line + ", destination='" + destination + "', stations=" + route.size() + ", firstStation='" + route.get(0)[0] + "', departures={" + sb.toString() + "} }");
                } catch (TimetableNotFoundException e) {
                    break;
                }
            }
        }
    }

}
