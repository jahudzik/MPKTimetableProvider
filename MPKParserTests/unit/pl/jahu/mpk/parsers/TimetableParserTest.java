package pl.jahu.mpk.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.TestUtils;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class TimetableParserTest {

    @Inject
    TimetableProvider timetableProvider;

    private TimetableParser timetableParser;


    @Before
    public void setUp() {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);
        timetableParser = new TimetableParser();
    }


    /******************** TESTS ********************/

    @Test
    public void parseDayTypes_standard() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(1);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);

        List<DayType> dayTypes = timetableParser.parseDayTypes(timetableDocument, lineNumber);

        checkDayType(dayTypes, new DayType[]{TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE});
    }

    @Test
    public void parseDayTypes_everydayNight() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(601);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);

        List<DayType> dayTypes = timetableParser.parseDayTypes(timetableDocument, lineNumber);

        checkDayType(dayTypes, new DayType[]{TestUtils.EVERYDAY_NIGHT_TYPE});
    }

    @Test
    public void parseDayTypes_specificNightDayTypes() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(605);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        DayType mondayThursdayType = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY}, true);
        DayType weekendNightType = DayType.getInstance(new int[]{Calendar.FRIDAY, Calendar.SATURDAY}, true);
        DayType sundayNightType = DayType.getInstance(new int[]{Calendar.SUNDAY}, true);

        List<DayType> dayTypes = timetableParser.parseDayTypes(timetableDocument, lineNumber);

        checkDayType(dayTypes, new DayType[]{mondayThursdayType, weekendNightType, sundayNightType});
    }




    @Test
    public void parseDepartures_weekday() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(1);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 0, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.WEEKDAY_TYPE, lineNumber, "Wzgórza Krzesławickie", "SALWATOR", 76);
        checkDeparture(timetable.getDepartures().get(0), 4, 37, null);
        checkDeparture(timetable.getDepartures().get(75), 22, 37, null);
    }

    @Test
    public void parseDepartures_saturday() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(1);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 1, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.SATURDAY_TYPE, lineNumber, "Wzgórza Krzesławickie", "SALWATOR", 53);
        checkDeparture(timetable.getDepartures().get(0), 4, 59, null);
        checkDeparture(timetable.getDepartures().get(52), 22, 47, null);
    }

    @Test
    public void parseDepartures_sunday() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(1);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 2, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.SUNDAY_TYPE, lineNumber, "Wzgórza Krzesławickie", "SALWATOR", 50);
        checkDeparture(timetable.getDepartures().get(0), 5, 20, null);
        checkDeparture(timetable.getDepartures().get(49), 22, 47, null);
    }

    @Test
    public void parseDepartures_everynight() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(601);
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.EVERYDAY_NIGHT_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 0, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.EVERYDAY_NIGHT_TYPE, lineNumber, "Mydlniki", "CZYŻYNY DWORZEC", 6);
        checkDeparture(timetable.getDepartures().get(0), 23, 30, null);
        checkDeparture(timetable.getDepartures().get(5),  4, 30, null);
    }

    @Test
    public void parseDepartures_withOneLegend() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(22);
        String expectedLegend = "Kurs do przystanku: Kombinat";
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 0, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.WEEKDAY_TYPE, lineNumber, "Borek Fałęcki", "WALCOWNIA", 62);
        checkDeparture(timetable.getDepartures().get(0), 5, 0, null);
        checkDeparture(timetable.getDepartures().get(61), 23, 36, new String[]{expectedLegend});
    }

    @Test
    public void parseDepartures_withTwoLegendsForOneDeparture() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(248);
        String expectedLegend1 = "Kurs do przystanku: Bolechowice przez: Kraków Business Park";
        String expectedLegend2 = "w dni nauki szkolnej do Bolechowic, w pozostałe dni powszednie do Zelkowa";
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 0, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.WEEKDAY_TYPE, lineNumber, "Bronowice Małe", "ZELKÓW", 16);
        checkDeparture(timetable.getDepartures().get(2), 6, 28, new String[]{expectedLegend1, expectedLegend2});
    }

    @Test
    public void parseDepartures_withTwoLegends() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(183);
        String expectedLegend1 = "Kurs do przystanku: Wydział Farmaceutyczny UJ";
        String expectedLegend2 = "Kurs do przystanku: Prokocim Szpital";
        ParsableData timetableDocument = timetableProvider.getTimetableDocument(lineNumber, 1);
        List<DayType> dayTypes = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);

        Timetable timetable = timetableParser.parseDepartures(timetableDocument, dayTypes, 0, lineNumber);

        checkTimetableGeneralInfo(timetable, TestUtils.WEEKDAY_TYPE, lineNumber, "Złocień", "DOM SPOKOJNEJ STAROŚCI", 37);
        checkDeparture(timetable.getDepartures().get(0), 4, 41, null);
        checkDeparture(timetable.getDepartures().get(35), 22, 8, new String[]{expectedLegend1});
        checkDeparture(timetable.getDepartures().get(36), 23, 5, new String[]{expectedLegend2});
    }


    /****************** API ********************/

    private void checkTimetableGeneralInfo(Timetable timetable, DayType expectedDayType, LineNumber expectedLineNumber,
                                           String expectedStation, String expectedDestination, int expectedDeparturesNumber ) {
        assertNotNull(timetable);
        assertEquals(expectedDayType, timetable.getDayType());
        assertEquals(expectedStation, timetable.getStation());
        assertEquals(expectedDestination, timetable.getDestStation());
        assertEquals(expectedLineNumber, timetable.getLine());
        assertEquals(expectedDeparturesNumber, timetable.getDepartures().size());
    }

    private void checkDayType(List<DayType> dayTypes, DayType[] expectedDayTypes) {
        TestUtils.checkCollectionSize(dayTypes, expectedDayTypes.length);
        for (DayType expectedDayType : expectedDayTypes) {
            assertTrue("Expected to find " + expectedDayType.toString() + " in " + TimetableProvider.dayTypesListToString(dayTypes), dayTypes.contains(expectedDayType));
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
