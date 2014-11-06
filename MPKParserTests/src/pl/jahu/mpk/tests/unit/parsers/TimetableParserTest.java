package pl.jahu.mpk.tests.unit.parsers;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tests.TestUtils;

import javax.inject.Inject;
import java.util.HashSet;
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
    public void testSupportedDayTypes() throws TimetableParseException {
        String inputHtml = "<html><body><table><tr>" +
                "<td>Dzień powszedni</td>" +
                "<td>Soboty</td>" +
                "<td>Święta</td>" +
                "<td>Wszystkie dni tygodnia</td>" +
                "</tr></body></table></html>";
        Elements inputElements = Jsoup.parse(inputHtml).getElementsByTag("td");

        Set<DayTypes> dayTypes = new HashSet<DayTypes>(TimetableParser.retrieveDayTypesConfiguration(inputElements, null));
        checkDayTypes(dayTypes, new DayTypes[]{DayTypes.WEEKDAY, DayTypes.SATURDAY, DayTypes.SUNDAY, DayTypes.EVERYDAY});
    }

    @Test(expected = TimetableParseException.class)
    public void testUnsupportedDayType() throws TimetableParseException {
        String inputHtml = "<html><body><table><tr>" +
                "<td>Poniedziałek</td>" +
                "<td>Soboty</td>" +
                "<td>Święta</td>" +
                "</tr></table></body></html>";
        Elements inputElements = Jsoup.parse(inputHtml).getElementsByTag("td");
        TimetableParser.retrieveDayTypesConfiguration(inputElements, null);
    }


    @Test
    public void testTimetable1Standard() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(1), 1);
        checkTimetableGeneralInfo(timetable, "Wzgórza Krzesławickie", "SALWATOR", 1);

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        checkDayTypesWithSizes(departures, new DayTypes[]{DayTypes.WEEKDAY, DayTypes.SATURDAY, DayTypes.SUNDAY}, new int[]{76, 53, 50});

        checkDeparture(departures.get(DayTypes.WEEKDAY).get(0), 4, 37, null);
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(75), 22, 37, null);
        checkDeparture(departures.get(DayTypes.SATURDAY).get(0), 4, 59, null);
        checkDeparture(departures.get(DayTypes.SATURDAY).get(52), 22, 47, null);
        checkDeparture(departures.get(DayTypes.SUNDAY).get(0), 5, 20, null);
        checkDeparture(departures.get(DayTypes.SUNDAY).get(49), 22, 47, null);
    }


    @Test
    public void testTimetable2WithLegend() throws ParsableDataNotFoundException, TimetableParseException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(22), 1);
        checkTimetableGeneralInfo(timetable, "Borek Fałęcki", "WALCOWNIA", 22);

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        checkDayTypesWithSizes(departures, new DayTypes[]{DayTypes.WEEKDAY, DayTypes.SATURDAY, DayTypes.SUNDAY}, new int[]{62, 54, 51});

        String expectedLegend = "Kurs do przystanku: Kombinat";
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(0), 5, 0, null);
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(61), 23, 36, new String[]{expectedLegend});
        checkDeparture(departures.get(DayTypes.SATURDAY).get(0), 5, 3, null);
        checkDeparture(departures.get(DayTypes.SATURDAY).get(53), 23, 4, new String[]{expectedLegend});
        checkDeparture(departures.get(DayTypes.SUNDAY).get(0), 5, 3, null);
        checkDeparture(departures.get(DayTypes.SUNDAY).get(50), 23, 6, new String[]{expectedLegend});
    }


    @Test
    public void testTimetable3WithEverydayType() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(601), 1);
        checkTimetableGeneralInfo(timetable, "Mydlniki", "CZYŻYNY DWORZEC", 601);

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        checkDayTypesWithSizes(departures, new DayTypes[]{DayTypes.EVERYDAY}, new int[]{6});

        checkDeparture(departures.get(DayTypes.EVERYDAY).get(0), 23, 30, null);
    }


    @Test
     public void testTimetable4WithWeekendSpecificDayTypes() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(605), 1);
        checkTimetableGeneralInfo(timetable, "Zajezdnia Płaszów", "BIELANY", 605);

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        checkDayTypesWithSizes(departures, new DayTypes[]{DayTypes.MONDAY_TO_THURSDAY, DayTypes.WEEKEND_NIGHTS, DayTypes.SUNDAY}, new int[]{5, 5, 5});

        String expectedLegend = "Kurs do przystanku: Dworzec Główny";
        checkDeparture(departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0), 23, 32, new String[]{expectedLegend});
        checkDeparture(departures.get(DayTypes.WEEKEND_NIGHTS).get(1), 0, 32, null);
        checkDeparture(departures.get(DayTypes.SUNDAY).get(0), 23, 32, new String[]{expectedLegend});
    }


    @Test
    public void testTimetable5WithTwoLegendEntries() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(183), 1);
        checkTimetableGeneralInfo(timetable, "Złocień", "DOM SPOKOJNEJ STAROŚCI", 183);

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        checkDayTypesWithSizes(departures, new DayTypes[]{DayTypes.WEEKDAY, DayTypes.SATURDAY, DayTypes.SUNDAY}, new int[]{37, 32, 30});

        String expectedLegend1 = "Kurs do przystanku: Wydział Farmaceutyczny UJ";
        String expectedLegend2 = "Kurs do przystanku: Prokocim Szpital";
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(0), 4, 41, null);
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(35), 22, 8, new String[]{expectedLegend1});
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(36), 23, 5, new String[]{expectedLegend2});
        checkDeparture(departures.get(DayTypes.SUNDAY).get(0), 4, 40, null);
        checkDeparture(departures.get(DayTypes.SUNDAY).get(28), 22, 49, new String[]{expectedLegend1});
        checkDeparture(departures.get(DayTypes.SUNDAY).get(29), 23, 35, new String[]{expectedLegend2});
    }


    @Test
    public void testTimetable6WithTwoLegendEntriesForOneDeparture() throws TimetableParseException, ParsableDataNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(248), 1);
        checkTimetableGeneralInfo(timetable, "Bronowice Małe", "ZELKÓW", 248);

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        checkDayTypesWithSizes(departures, new DayTypes[]{DayTypes.WEEKDAY, DayTypes.SATURDAY, DayTypes.SUNDAY}, new int[]{16, 12, 12});

        String expectedLegend1 = "Kurs do przystanku: Bolechowice przez: Kraków Business Park";
        String expectedLegend2 = "w dni nauki szkolnej do Bolechowic, w pozostałe dni powszednie do Zelkowa";
        checkDeparture(departures.get(DayTypes.WEEKDAY).get(2), 6, 28, new String[]{expectedLegend1, expectedLegend2});
    }


    /****************** API ********************/

    private void checkTimetableGeneralInfo(Timetable timetable, String expectedStation, String expectedDestination, int expectedLineNumber) {
        assertNotNull(timetable);
        assertEquals(expectedStation, timetable.getStation());
        assertEquals(expectedDestination, timetable.getDestStation());
        assertEquals(expectedLineNumber, timetable.getLine().getNumeric());
    }

    private void checkDayTypesWithSizes(Map<DayTypes, List<Departure>> departures, DayTypes[] expectedDayTypes, int[] expectedSizes) {
        checkDayTypes(departures.keySet(), expectedDayTypes);
        assertEquals(expectedSizes.length, departures.keySet().size());
        for (int i = 0; i < expectedDayTypes.length; i++) {
            assertEquals(departures.get(expectedDayTypes[i]).size(), expectedSizes[i]);
        }
    }

    private void checkDayTypes(Set<DayTypes> dayTypes, DayTypes[] expectedDayTypes) {
        TestUtils.checkCollectionSize(dayTypes, expectedDayTypes.length);
        for (DayTypes expectedDayType : expectedDayTypes) {
            assertTrue(dayTypes.contains(expectedDayType));
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
