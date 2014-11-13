package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tests.TestUtils;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TimetableParserTest {

    @Inject
    TimetableProvider timetableProvider;


    @Before
    public void setUp() {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);
    }


    /******************** TESTS ********************/


    @Test
    public void testTimetable1Standard() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(1), 1);
        checkTimetableGeneralInfo(timetable, "Wzgórza Krzesławickie", "SALWATOR", 1);

        Map<DayType, List<Departure>> departures = timetable.getDepartures();

        
        checkDayTypeWithSizes(departures, new DayType[]{TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE}, new int[]{76, 53, 50});

        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(0), 4, 37, null);
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(75), 22, 37, null);
        checkDeparture(departures.get(TestUtils.SATURDAY_TYPE).get(0), 4, 59, null);
        checkDeparture(departures.get(TestUtils.SATURDAY_TYPE).get(52), 22, 47, null);
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(0), 5, 20, null);
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(49), 22, 47, null);
    }


    @Test
    public void testTimetable2WithLegend() throws ParsableDataNotFoundException, TimetableParseException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(22), 1);
        checkTimetableGeneralInfo(timetable, "Borek Fałęcki", "WALCOWNIA", 22);

        Map<DayType, List<Departure>> departures = timetable.getDepartures();
        checkDayTypeWithSizes(departures, new DayType[]{TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE}, new int[]{62, 54, 51});

        String expectedLegend = "Kurs do przystanku: Kombinat";
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(0), 5, 0, null);
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(61), 23, 36, new String[]{expectedLegend});
        checkDeparture(departures.get(TestUtils.SATURDAY_TYPE).get(0), 5, 3, null);
        checkDeparture(departures.get(TestUtils.SATURDAY_TYPE).get(53), 23, 4, new String[]{expectedLegend});
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(0), 5, 3, null);
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(50), 23, 6, new String[]{expectedLegend});
    }


    @Test
    public void testTimetable3WithEverydayType() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(601), 1);
        checkTimetableGeneralInfo(timetable, "Mydlniki", "CZYŻYNY DWORZEC", 601);

        Map<DayType, List<Departure>> departures = timetable.getDepartures();
        checkDayTypeWithSizes(departures, new DayType[]{TestUtils.EVERYDAY_NIGHT_TYPE}, new int[]{6});

        checkDeparture(departures.get(TestUtils.EVERYDAY_NIGHT_TYPE).get(0), 23, 30, null);
    }


    @Test
     public void testTimetable4WithWeekendSpecificDayType() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(605), 1);
        checkTimetableGeneralInfo(timetable, "Zajezdnia Płaszów", "BIELANY", 605);

        Map<DayType, List<Departure>> departures = timetable.getDepartures();
        DayType mondayThursdayType = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY}, true);
        DayType weekendNightType = DayType.getInstance(new int[]{Calendar.FRIDAY, Calendar.SATURDAY}, true);
        checkDayTypeWithSizes(departures, new DayType[]{mondayThursdayType, weekendNightType, TestUtils.SUNDAY_NIGHT_TYPE}, new int[]{5, 5, 5});

        String expectedLegend = "Kurs do przystanku: Dworzec Główny";
        checkDeparture(departures.get(mondayThursdayType).get(0), 23, 32, new String[]{expectedLegend});
        checkDeparture(departures.get(weekendNightType).get(1), 0, 32, null);
        checkDeparture(departures.get(TestUtils.SUNDAY_NIGHT_TYPE).get(0), 23, 32, new String[]{expectedLegend});
    }


    @Test
    public void testTimetable5WithTwoLegendEntries() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(183), 1);
        checkTimetableGeneralInfo(timetable, "Złocień", "DOM SPOKOJNEJ STAROŚCI", 183);

        Map<DayType, List<Departure>> departures = timetable.getDepartures();
        checkDayTypeWithSizes(departures, new DayType[]{TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE}, new int[]{37, 32, 30});

        String expectedLegend1 = "Kurs do przystanku: Wydział Farmaceutyczny UJ";
        String expectedLegend2 = "Kurs do przystanku: Prokocim Szpital";
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(0), 4, 41, null);
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(35), 22, 8, new String[]{expectedLegend1});
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(36), 23, 5, new String[]{expectedLegend2});
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(0), 4, 40, null);
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(28), 22, 49, new String[]{expectedLegend1});
        checkDeparture(departures.get(TestUtils.SUNDAY_TYPE).get(29), 23, 35, new String[]{expectedLegend2});
    }


    @Test
    public void testTimetable6WithTwoLegendEntriesForOneDeparture() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(248), 1);
        checkTimetableGeneralInfo(timetable, "Bronowice Małe", "ZELKÓW", 248);

        Map<DayType, List<Departure>> departures = timetable.getDepartures();
        checkDayTypeWithSizes(departures, new DayType[]{TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE}, new int[]{16, 12, 12});

        String expectedLegend1 = "Kurs do przystanku: Bolechowice przez: Kraków Business Park";
        String expectedLegend2 = "w dni nauki szkolnej do Bolechowic, w pozostałe dni powszednie do Zelkowa";
        checkDeparture(departures.get(TestUtils.WEEKDAY_TYPE).get(2), 6, 28, new String[]{expectedLegend1, expectedLegend2});
    }


    /****************** API ********************/

    private void checkTimetableGeneralInfo(Timetable timetable, String expectedStation, String expectedDestination, int expectedLineNumber) {
        assertNotNull(timetable);
        assertEquals(expectedStation, timetable.getStation());
        assertEquals(expectedDestination, timetable.getDestStation());
        assertEquals(expectedLineNumber, timetable.getLine().getNumeric());
    }

    private void checkDayTypeWithSizes(Map<DayType, List<Departure>> departures, DayType[] expectedDayType, int[] expectedSizes) {
        checkDayType(departures.keySet(), expectedDayType);
        assertEquals(expectedSizes.length, departures.keySet().size());
        for (int i = 0; i < expectedDayType.length; i++) {
            assertEquals(departures.get(expectedDayType[i]).size(), expectedSizes[i]);
        }
    }

    private void checkDayType(Set<DayType> dayTypes, DayType[] expectedDayTypes) {
        TestUtils.checkCollectionSize(dayTypes, expectedDayTypes.length);
        for (DayType expectedDayType : expectedDayTypes) {
            assertTrue("Expected to find " + expectedDayType.toString(), dayTypes.contains(expectedDayType));
        }
    }

    private void checkDeparture(Departure departure, int expectedHour, int expectedMinute, String[] expectedLegends) {
        assertNotNull(departure);
        assertEquals(expectedHour, departure.getHour());
        assertEquals(expectedMinute, departure.getMin());
        if (expectedLegends != null) {
            for (int i = 0; i < expectedLegends.length; i++) {
                assertEquals(expectedLegends[i], departure.getExtraInfo()[i]);
            }
        } else {
            assertNull(departure.getExtraInfo());
        }
    }

}
