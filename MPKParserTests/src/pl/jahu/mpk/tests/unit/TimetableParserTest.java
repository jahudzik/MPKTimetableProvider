package pl.jahu.mpk.tests.unit;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.Test;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TimetableParserTest {

    @Test(expected = TimetableNotFoundException.class)
    public void testIncorrectTimetableUrl() throws TimetableParseException, TimetableNotFoundException {
        new TimetableParser(12345,"123");
    }

    @Test
    public void testCorrectCase() throws TimetableParseException, TimetableNotFoundException {
        TimetableParser timetableParser = new TimetableParser(1, "0001t001.htm");
        assertNotNull(timetableParser.getStopName());
        assertTrue(timetableParser.getStopName().length() > 0);
    }


    @Test
    public void testSupportedDayTypes() {
        String inputHtml = "<html><body><table><tr>" +
                "<td>Dzień powszedni</td>" +
                "<td>Soboty</td>" +
                "<td>Święta</td>" +
                "<td>Wszystkie dni tygodnia</td>" +
                "</tr></body></table></html>";
        Elements inputElements = Jsoup.parse(inputHtml).getElementsByTag("td");

        try {
            List<DayTypes> dayTypes = TimetableParser.retrieveDayTypesConfiguration(inputElements);
            assertEquals(4, dayTypes.size());
            assertEquals(DayTypes.WEEKDAY, dayTypes.get(0));
            assertEquals(DayTypes.SATURDAY, dayTypes.get(1));
            assertEquals(DayTypes.SUNDAY, dayTypes.get(2));
            assertEquals(DayTypes.EVERYDAY, dayTypes.get(3));
        } catch (TimetableParseException e) {
            fail();
        }
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
    public void testTimetable1Standard() {
        String expectedStopName = "Wzgórza Krzesławickie";
        String expectedDestination = "SALWATOR";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable1.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(expectedStopName, parser.getStopName());
            assertEquals(expectedDestination, parser.getDestination());

            Map<DayTypes, List<Departure>> departures = parser.parse();

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

        } catch (IOException e) {
            fail();
        } catch (TimetableParseException e) {
            fail();
        }
    }


    @Test
    public void testTimetable2WithLegend() {
        String expectedStopName = "Borek Fałęcki";
        String expectedDestination = "WALCOWNIA";
        String expectedLegend = "Kurs do przystanku: Kombinat";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable2.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(expectedStopName, parser.getStopName());
            assertEquals(expectedDestination, parser.getDestination());

            Map<DayTypes, List<Departure>> departures = parser.parse();

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

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


    @Test
    public void testTimetable3WithEverydayType() {
        String expectedStopName = "Mydlniki";
        String expectedDestination = "CZYŻYNY DWORZEC";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable3.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(expectedStopName, parser.getStopName());
            assertEquals(expectedDestination, parser.getDestination());

            Map<DayTypes, List<Departure>> departures = parser.parse();

            assertEquals(1, departures.size());
            assertTrue(departures.containsKey(DayTypes.EVERYDAY));

            assertEquals(6, departures.get(DayTypes.EVERYDAY).size());

            assertEquals(23, departures.get(DayTypes.EVERYDAY).get(0).getHour());
            assertEquals(30, departures.get(DayTypes.EVERYDAY).get(0).getMin());
            assertNull(departures.get(DayTypes.EVERYDAY).get(0).getExtraInfo());

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


    @Test
     public void testTimetable4WithWeekendSpecificDayTypes() {
        String expectedStopName = "Zajezdnia Płaszów";
        String expectedDestination = "BIELANY";
        String expectedLegend = "Kurs do przystanku: Dworzec Główny";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable4.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(expectedStopName, parser.getStopName());
            assertEquals(expectedDestination, parser.getDestination());

            Map<DayTypes, List<Departure>> departures = parser.parse();

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

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


    @Test
    public void testTimetable5WithTwoLegendEntries() {
        String expectedStopName = "Złocień";
        String expectedDestination = "DOM SPOKOJNEJ STAROŚCI";
        String expectedLegend1 = "Kurs do przystanku: Wydział Farmaceutyczny UJ";
        String expectedLegend2 = "Kurs do przystanku: Prokocim Szpital";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable5.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(expectedStopName, parser.getStopName());
            assertEquals(expectedDestination, parser.getDestination());

            Map<DayTypes, List<Departure>> departures = parser.parse();

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

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }



    @Test
    public void testTimetable6WithTwoLegendEntriesForOneDeparture() {
        String expectedStopName = "Bronowice Małe";
        String expectedDestination = "ZELKÓW";
        String expectedLegend1 = "Kurs do przystanku: Bolechowice przez: Kraków Business Park";
        String expectedLegend2 = "w dni nauki szkolnej do Bolechowic, w pozostałe dni powszednie do Zelkowa";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable6.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(expectedStopName, parser.getStopName());
            assertEquals(expectedDestination, parser.getDestination());

            Map<DayTypes, List<Departure>> departures = parser.parse();

            assertEquals(6, departures.get(DayTypes.WEEKDAY).get(2).getHour());
            assertEquals(28, departures.get(DayTypes.WEEKDAY).get(2).getMin());

            String[] extraInfo = departures.get(DayTypes.WEEKDAY).get(2).getExtraInfo();
            assertNotNull(extraInfo);
            assertEquals(2, extraInfo.length);
            assertTrue(extraInfo[0].equals(expectedLegend1) || extraInfo[0].equals(expectedLegend2));
            assertTrue(extraInfo[1].equals(expectedLegend1) || extraInfo[1].equals(expectedLegend2));
            assertNotEquals(extraInfo[0], extraInfo[1]);
        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


}
