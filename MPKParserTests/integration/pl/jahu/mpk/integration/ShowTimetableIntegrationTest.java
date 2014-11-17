package pl.jahu.mpk.integration;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.entities.*;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tools.TransitBuilder;
import pl.jahu.mpk.utils.LineNumbersResolver;
import pl.jahu.mpk.utils.TimeUtils;
import pl.jahu.mpk.validators.exceptions.TransitValidationException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jahudzik on 2014-08-03.
 *
 * Based on Scenario1ShowLineTimetable
 */
public class ShowTimetableIntegrationTest {

    private static Line actLine;
    private static String destination;
    private static int actSequenceNumber;
    private static boolean printGeneralOutput;
    private static Date testDate;

    @Inject
    static TimetableProvider timetableProvider;

    @Before
    public void setUp() {
        DaggerApplication.init(new DefaultTestModule());
        testDate = TimeUtils.buildDate(12, 11, 2014);
    }

    @Test
    public void testShowingTimetable() {
        try {
            showTimetable(0, 1000, true, false);
        } catch (ParsableDataNotFoundException | TimetableParseException | TransitValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    @AfterClass
    public static void logAfter() {
        if (printGeneralOutput) {
            System.out.println("LAST EXECUTED: {line=" + actLine + ", destination='" + destination + "', seq=" + actSequenceNumber + "}");
        }
    }

    public static void showTimetable(int firstLine, int lastLine, boolean printGeneralOutput, boolean printDetailedOutput) throws ParsableDataNotFoundException, TimetableParseException, TransitValidationException {
        ShowTimetableIntegrationTest.printGeneralOutput = printGeneralOutput;
        List<Line> lines = timetableProvider.getLinesList();
        assertNotNull(lines);
        assertTrue(lines.size() > 0);

        int[] linesRange = LineNumbersResolver.getLinesFromRange(lines, new LineNumber(firstLine), new LineNumber(lastLine));

        // for each line...
        for (int k = linesRange[0]; k <= linesRange[1]; k++) {
            Line line = lines.get(k);
            actLine = line;

            // for each destination...
            for (int i = 1; i < 10; i++) {
                try {
                    destination = timetableProvider.getLineRouteDestination(line.getNumber(), i);

                    // get all stations on the route
                    List<StationData> route = timetableProvider.getLineRoute(line.getNumber(), i);
                    assertNotNull(route);
                    assertTrue(route.size() > 0);
                    List<Timetable> timetables = new ArrayList<>();

                    // for each station on the route...
                    for (StationData station : route) {
                        assertNotNull(station);
                        assertNotNull(station.getName());
                        assertNotNull(station.getLineNumber());
                        assertNotNull(station.getSequenceNumber());

                        // parse and save timetable
                        actSequenceNumber = station.getSequenceNumber();
                        Timetable timetable = timetableProvider.getTimetable(testDate, line.getNumber(), station.getSequenceNumber());
                        List<Departure> departures = timetable.getDepartures();
                        assertNotNull(departures);
                        assertTrue(departures.size() > 0);
                        timetables.add(timetable);
                    }

                    // get list of all transits on the route (based on timetables)
                    List<Transit> transits = TransitBuilder.buildFromTimetables(timetables);
                    assertNotNull(transits);
                    assertTrue(transits.size() > 0);

                    DayType dayType = timetables.get(0).getDayType();
                    // prints all transits
                    if (printDetailedOutput) {
                        TransitBuilder.printTransitsList(dayType, transits);
                    }

                    // prints general summary of each destination of each line
                    if (printGeneralOutput) {
                        System.out.println("PASSED: {line=" + line + ", route='" + route.get(0).getName() + "'->'" + destination + "', stations=" + route.size() + ", departures={" + String.valueOf(dayType) + "=" + transits.size() + ";" + "} }");
                    }
                } catch (ParsableDataNotFoundException e) {
                    break;
                }
            }
        }
    }

}
