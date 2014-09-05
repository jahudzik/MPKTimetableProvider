package pl.jahu.mpk.tests.integration;

import org.junit.AfterClass;
import org.junit.Test;
import pl.jahu.mpk.TransitBuilder;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.entities.Transit;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.LineRouteParser;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;
import pl.jahu.mpk.parser.utils.LineNumbersResolver;
import pl.jahu.mpk.validators.exceptions.TransitValidationException;

import java.util.ArrayList;
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
    private static boolean printGeneralOutput;

    @Test
    public void testShowingTimetable() {
        try {
            showTimetable(0, 1000, true, false);
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (LineRouteParseException e) {
            e.printStackTrace();
            fail();
        } catch (TimetableParseException e) {
            e.printStackTrace();
            fail();
        } catch (TransitValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    @AfterClass
    public static void logAfter() {
        if (printGeneralOutput) {
            System.out.println("LAST EXECUTED: {line=" + actLine + ", destination='" + destination + "', url=" + actTimetableUrl + "}");
        }
    }

    public static void showTimetable(int firstLine, int lastLine, boolean printGeneralOutput, boolean printDetailedOutput) throws TimetableNotFoundException, LineRouteParseException, TimetableParseException, TransitValidationException {
        ShowTimetableIntegrationTest.printGeneralOutput = printGeneralOutput;
        LinesListParser linesListParser = new LinesListParser();
        List<Integer> lines = linesListParser.parse();
        assertNotNull(lines);
        assertTrue(lines.size() > 0);

        int[] linesRange = LineNumbersResolver.getLinesFromRange(lines, firstLine, lastLine);

        // for each line...
        for (int k = linesRange[0]; k <= linesRange[1]; k++) {
            int line = lines.get(k);
            actLine = line;

            // for each destination...
            for (int i = 1; i < 10; i++) {
                try {
                    LineRouteParser routeParser = new LineRouteParser(line, i);
                    destination = routeParser.getDestination();

                    // get all stations on the route
                    List<String[]> route = routeParser.parse();
                    assertNotNull(route);
                    assertTrue(route.size() > 0);
                    List<Timetable> timetables = new ArrayList<Timetable>();

                    // for each station on the route...
                    for (String[] station : route) {
                        assertNotNull(station);
                        assertEquals(2, station.length);
                        assertNotNull(station[0]);
                        assertNotNull(station[1]);

                        // parse and save timetable
                        TimetableParser timetableParser = new TimetableParser(line, station[1]);
                        actTimetableUrl = timetableParser.getUrl();
                        Timetable timetable = timetableParser.parse();
                        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
                        assertNotNull(departures);
                        assertTrue(departures.size() > 0);
                        timetables.add(timetable);
                    }

                    // get list of all transits on the route (based on timetables)
                    Map<DayTypes, List<Transit>> transitsMap = TransitBuilder.buildFromTimetables(timetables);
                    assertNotNull(transitsMap);
                    assertTrue(transitsMap.size() > 0);

                    // prints all transits
                    if (printDetailedOutput) {
                        TransitBuilder.printTransitsMap(transitsMap);
                    }

                    // prints general summary of each destination of each line
                    if (printGeneralOutput) {
                        StringBuilder sb = new StringBuilder();
                        for (DayTypes dayType : transitsMap.keySet()) {
                            List<Transit> transits = transitsMap.get(dayType);
                            sb.append(dayType).append("=").append(transits.size()).append(";");
                        }
                        System.out.println("PASSED: {line=" + line + ", route='" + route.get(0)[0] + "'->'" + destination + "', stations=" + route.size() + ", departures={" + sb.toString() + "} }");
                    }
                } catch (TimetableNotFoundException e) {
                    break;
                }
            }
        }
    }

}
