package pl.jahu.mpk.tests.unit.parsers;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerTestApplication;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TimetableParserTest {

    @Inject
    TimetableProvider timetableProvider;

    @Before
    public void setUp() {
        DaggerTestApplication.init();
        DaggerTestApplication.inject(this);
    }

    @Test
    public void testSupportedDayTypes() throws TimetableParseException {
        String inputHtml = "<html><body><table><tr>" +
                "<td>Dzień powszedni</td>" +
                "<td>Soboty</td>" +
                "<td>Święta</td>" +
                "<td>Wszystkie dni tygodnia</td>" +
                "</tr></body></table></html>";
        Elements inputElements = Jsoup.parse(inputHtml).getElementsByTag("td");

        List<DayTypes> dayTypes = TimetableParser.retrieveDayTypesConfiguration(inputElements);
        assertEquals(4, dayTypes.size());
        assertEquals(DayTypes.WEEKDAY, dayTypes.get(0));
        assertEquals(DayTypes.SATURDAY, dayTypes.get(1));
        assertEquals(DayTypes.SUNDAY, dayTypes.get(2));
        assertEquals(DayTypes.EVERYDAY, dayTypes.get(3));
    }

    @Test(expected = TimetableParseException.class)
    public void testUnsupportedDayType() throws TimetableParseException {
        String inputHtml = "<html><body><table><tr>" +
                "<td>Poniedziałek</td>" +
                "<td>Soboty</td>" +
                "<td>Święta</td>" +
                "</tr></table></body></html>";
        Elements inputElements = Jsoup.parse(inputHtml).getElementsByTag("td");
        TimetableParser.retrieveDayTypesConfiguration(inputElements);
    }


    @Test
    public void testTimetable1Standard() throws UnsupportedLineNumberException, TimetableParseException, TimetableNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(1), "0001t001.htm");
        assertEquals("SALWATOR", timetable.getDestStation());
        assertEquals("Wzgórza Krzesławickie", timetable.getStation());
        assertEquals(1, timetable.getLine().getNumeric());

        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();
        assertEquals(3, departures.size());
        assertTrue(departures.containsKey(DayTypes.WEEKDAY));
        assertTrue(departures.containsKey(DayTypes.SATURDAY));
        assertTrue(departures.containsKey(DayTypes.SUNDAY));

        assertEquals(76, departures.get(DayTypes.WEEKDAY).size());
        assertEquals(53, departures.get(DayTypes.SATURDAY).size());
        assertEquals(50, departures.get(DayTypes.SUNDAY).size());

        assertEquals(4, departures.get(DayTypes.WEEKDAY).get(0).getHour());
        assertEquals(37, departures.get(DayTypes.WEEKDAY).get(0).getMin());
        assertEquals(22, departures.get(DayTypes.WEEKDAY).get(75).getHour());
        assertEquals(37, departures.get(DayTypes.WEEKDAY).get(75).getMin());

        assertEquals(4, departures.get(DayTypes.SATURDAY).get(0).getHour());
        assertEquals(59, departures.get(DayTypes.SATURDAY).get(0).getMin());
        assertEquals(22, departures.get(DayTypes.SATURDAY).get(52).getHour());
        assertEquals(47, departures.get(DayTypes.SATURDAY).get(52).getMin());

        assertEquals(5, departures.get(DayTypes.SUNDAY).get(0).getHour());
        assertEquals(20, departures.get(DayTypes.SUNDAY).get(0).getMin());
        assertEquals(22, departures.get(DayTypes.SUNDAY).get(49).getHour());
        assertEquals(47, departures.get(DayTypes.SUNDAY).get(49).getMin());
    }


    @Test
    public void testTimetable2WithLegend() throws UnsupportedLineNumberException, TimetableNotFoundException, TimetableParseException {
        String expectedLegend = "Kurs do przystanku: Kombinat";

        Timetable timetable = timetableProvider.getTimetable(new LineNumber(22), "0022t0001.htm");
        assertEquals("WALCOWNIA", timetable.getDestStation());
        assertEquals("Borek Fałęcki", timetable.getStation());
        assertEquals(22, timetable.getLine().getNumeric());
        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();

        assertEquals(3, departures.size());
        assertTrue(departures.containsKey(DayTypes.WEEKDAY));
        assertTrue(departures.containsKey(DayTypes.SATURDAY));
        assertTrue(departures.containsKey(DayTypes.SUNDAY));

        assertEquals(62, departures.get(DayTypes.WEEKDAY).size());
        assertEquals(54, departures.get(DayTypes.SATURDAY).size());
        assertEquals(51, departures.get(DayTypes.SUNDAY).size());

        assertEquals(5, departures.get(DayTypes.WEEKDAY).get(0).getHour());
        assertEquals(0, departures.get(DayTypes.WEEKDAY).get(0).getMin());
        assertNull(departures.get(DayTypes.WEEKDAY).get(0).getExtraInfo());
        assertEquals(23, departures.get(DayTypes.WEEKDAY).get(61).getHour());
        assertEquals(36, departures.get(DayTypes.WEEKDAY).get(61).getMin());
        assertEquals(expectedLegend, departures.get(DayTypes.WEEKDAY).get(61).getExtraInfo()[0]);

        assertEquals(5, departures.get(DayTypes.SATURDAY).get(0).getHour());
        assertNull(departures.get(DayTypes.SATURDAY).get(0).getExtraInfo());
        assertEquals(3, departures.get(DayTypes.SATURDAY).get(0).getMin());
        assertEquals(23, departures.get(DayTypes.SATURDAY).get(53).getHour());
        assertEquals(4, departures.get(DayTypes.SATURDAY).get(53).getMin());
        assertEquals(expectedLegend, departures.get(DayTypes.SATURDAY).get(53).getExtraInfo()[0]);

        assertEquals(5, departures.get(DayTypes.SUNDAY).get(0).getHour());
        assertEquals(3, departures.get(DayTypes.SUNDAY).get(0).getMin());
        assertNull(departures.get(DayTypes.SUNDAY).get(0).getExtraInfo());
        assertEquals(23, departures.get(DayTypes.SUNDAY).get(50).getHour());
        assertEquals(6, departures.get(DayTypes.SUNDAY).get(50).getMin());
        assertEquals(expectedLegend, departures.get(DayTypes.SUNDAY).get(50).getExtraInfo()[0]);
    }


    @Test
    public void testTimetable3WithEverydayType() throws UnsupportedLineNumberException, TimetableParseException, TimetableNotFoundException {
        Timetable timetable = timetableProvider.getTimetable(new LineNumber(601), "0601t0001.htm");
        assertEquals("CZYŻYNY DWORZEC", timetable.getDestStation());
        assertEquals("Mydlniki", timetable.getStation());
        assertEquals(601, timetable.getLine().getNumeric());
        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();

        assertEquals(1, departures.size());
        assertTrue(departures.containsKey(DayTypes.EVERYDAY));

        assertEquals(6, departures.get(DayTypes.EVERYDAY).size());

        assertEquals(23, departures.get(DayTypes.EVERYDAY).get(0).getHour());
        assertEquals(30, departures.get(DayTypes.EVERYDAY).get(0).getMin());
        assertNull(departures.get(DayTypes.EVERYDAY).get(0).getExtraInfo());
    }


    @Test
     public void testTimetable4WithWeekendSpecificDayTypes() throws UnsupportedLineNumberException, TimetableParseException, TimetableNotFoundException {
        String expectedLegend = "Kurs do przystanku: Dworzec Główny";

        Timetable timetable = timetableProvider.getTimetable(new LineNumber(605), "0605t0001.htm");
        assertEquals("BIELANY", timetable.getDestStation());
        assertEquals("Zajezdnia Płaszów", timetable.getStation());
        assertEquals(605, timetable.getLine().getNumeric());
        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();

        assertEquals(3, departures.size());
        assertTrue(departures.containsKey(DayTypes.MONDAY_TO_THURSDAY));
        assertTrue(departures.containsKey(DayTypes.WEEKEND_NIGHTS));
        assertTrue(departures.containsKey(DayTypes.SUNDAY));

        assertEquals(5, departures.get(DayTypes.MONDAY_TO_THURSDAY).size());
        assertEquals(5, departures.get(DayTypes.WEEKEND_NIGHTS).size());
        assertEquals(5, departures.get(DayTypes.SUNDAY).size());

        assertEquals(23, departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0).getHour());
        assertEquals(32, departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0).getMin());
        assertEquals(expectedLegend, departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0).getExtraInfo()[0]);

        assertEquals(0, departures.get(DayTypes.WEEKEND_NIGHTS).get(1).getHour());
        assertEquals(32, departures.get(DayTypes.WEEKEND_NIGHTS).get(1).getMin());
        assertNull(departures.get(DayTypes.WEEKEND_NIGHTS).get(1).getExtraInfo());

        assertEquals(23, departures.get(DayTypes.SUNDAY).get(0).getHour());
        assertEquals(32, departures.get(DayTypes.SUNDAY).get(0).getMin());
        assertEquals(expectedLegend, departures.get(DayTypes.SUNDAY).get(0).getExtraInfo()[0]);
    }


    @Test
    public void testTimetable5WithTwoLegendEntries() throws UnsupportedLineNumberException, TimetableParseException, TimetableNotFoundException {
        String expectedLegend1 = "Kurs do przystanku: Wydział Farmaceutyczny UJ";
        String expectedLegend2 = "Kurs do przystanku: Prokocim Szpital";

        Timetable timetable = timetableProvider.getTimetable(new LineNumber(183), "0183t0001.htm");
        assertEquals("DOM SPOKOJNEJ STAROŚCI", timetable.getDestStation());
        assertEquals("Złocień", timetable.getStation());
        assertEquals(183, timetable.getLine().getNumeric());
        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();

        assertEquals(3, departures.size());
        assertTrue(departures.containsKey(DayTypes.WEEKDAY));
        assertTrue(departures.containsKey(DayTypes.SATURDAY));
        assertTrue(departures.containsKey(DayTypes.SUNDAY));

        assertEquals(37, departures.get(DayTypes.WEEKDAY).size());
        assertEquals(32, departures.get(DayTypes.SATURDAY).size());
        assertEquals(30, departures.get(DayTypes.SUNDAY).size());

        assertEquals(4, departures.get(DayTypes.WEEKDAY).get(0).getHour());
        assertEquals(41, departures.get(DayTypes.WEEKDAY).get(0).getMin());
        assertNull(departures.get(DayTypes.WEEKDAY).get(0).getExtraInfo());

        assertEquals(22, departures.get(DayTypes.WEEKDAY).get(35).getHour());
        assertEquals(8, departures.get(DayTypes.WEEKDAY).get(35).getMin());
        assertEquals(expectedLegend1, departures.get(DayTypes.WEEKDAY).get(35).getExtraInfo()[0]);

        assertEquals(23, departures.get(DayTypes.WEEKDAY).get(36).getHour());
        assertEquals(5, departures.get(DayTypes.WEEKDAY).get(36).getMin());
        assertEquals(expectedLegend2, departures.get(DayTypes.WEEKDAY).get(36).getExtraInfo()[0]);

        assertEquals(4, departures.get(DayTypes.SUNDAY).get(0).getHour());
        assertEquals(40, departures.get(DayTypes.SUNDAY).get(0).getMin());
        assertNull(departures.get(DayTypes.SUNDAY).get(0).getExtraInfo());

        assertEquals(22, departures.get(DayTypes.SUNDAY).get(28).getHour());
        assertEquals(49, departures.get(DayTypes.SUNDAY).get(28).getMin());
        assertEquals(expectedLegend1, departures.get(DayTypes.SUNDAY).get(28).getExtraInfo()[0]);

        assertEquals(23, departures.get(DayTypes.SUNDAY).get(29).getHour());
        assertEquals(35, departures.get(DayTypes.SUNDAY).get(29).getMin());
        assertEquals(expectedLegend2, departures.get(DayTypes.SUNDAY).get(29).getExtraInfo()[0]);
    }



    @Test
    public void testTimetable6WithTwoLegendEntriesForOneDeparture() throws UnsupportedLineNumberException, TimetableParseException, TimetableNotFoundException {
        String expectedLegend1 = "Kurs do przystanku: Bolechowice przez: Kraków Business Park";
        String expectedLegend2 = "w dni nauki szkolnej do Bolechowic, w pozostałe dni powszednie do Zelkowa";

        Timetable timetable = timetableProvider.getTimetable(new LineNumber(248), "0248t0001.htm");
        assertEquals("ZELKÓW", timetable.getDestStation());
        assertEquals("Bronowice Małe", timetable.getStation());
        assertEquals(248, timetable.getLine().getNumeric());
        Map<DayTypes, List<Departure>> departures = timetable.getDepartures();

        assertEquals(6, departures.get(DayTypes.WEEKDAY).get(2).getHour());
        assertEquals(28, departures.get(DayTypes.WEEKDAY).get(2).getMin());

        String[] extraInfo = departures.get(DayTypes.WEEKDAY).get(2).getExtraInfo();
        assertNotNull(extraInfo);
        assertEquals(2, extraInfo.length);
        assertTrue(extraInfo[0].equals(expectedLegend1) || extraInfo[0].equals(expectedLegend2));
        assertTrue(extraInfo[1].equals(expectedLegend1) || extraInfo[1].equals(expectedLegend2));
        assertNotEquals(extraInfo[0], extraInfo[1]);
    }


}
