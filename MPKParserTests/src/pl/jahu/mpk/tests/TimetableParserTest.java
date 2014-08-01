package pl.jahu.mpk.tests;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.Station;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TimetableParserTest {

    @Test(expected = TimetableNotFoundException.class)
    public void testIncorrectTimetableUrl() throws TimetableParseException, TimetableNotFoundException {
        TimetableParser timetableParser = new TimetableParser("http://rozklady.mpk.krakow.pl/aktualne/000123456/0002341t001.htm");
    }

    @Test
    public void testCorrectCase() throws TimetableParseException, TimetableNotFoundException {
        TimetableParser timetableParser = new TimetableParser("http://rozklady.mpk.krakow.pl/aktualne/0001/0001t001.htm");
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
            assertEquals(dayTypes.size(), 4);
            assertEquals(dayTypes.get(0), DayTypes.WEEKDAY);
            assertEquals(dayTypes.get(1), DayTypes.SATURDAY);
            assertEquals(dayTypes.get(2), DayTypes.SUNDAY);
            assertEquals(dayTypes.get(3), DayTypes.EVERYDAY);
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
        List<DayTypes> dayTypes = TimetableParser.retrieveDayTypesConfiguration(inputElements);
    }


    @Test
    public void testTimetable1Standard() {
        String expectedStopName = "Wzgórza Krzesławickie";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable1.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(parser.getStopName(), expectedStopName);

            Map<DayTypes, List<Departure>> departures = parser.parse(new Station(expectedStopName));

            assertEquals(departures.size(), 3);
            assertTrue(departures.containsKey(DayTypes.WEEKDAY));
            assertTrue(departures.containsKey(DayTypes.SATURDAY));
            assertTrue(departures.containsKey(DayTypes.SUNDAY));

            assertEquals(departures.get(DayTypes.WEEKDAY).size(), 76);
            assertEquals(departures.get(DayTypes.SATURDAY).size(), 53);
            assertEquals(departures.get(DayTypes.SUNDAY).size(), 50);

            assertEquals(departures.get(DayTypes.WEEKDAY).get(0).getHour(), 4);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(0).getMin(), 37);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(75).getHour(), 22);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(75).getMin(), 37);

            assertEquals(departures.get(DayTypes.SATURDAY).get(0).getHour(), 4);
            assertEquals(departures.get(DayTypes.SATURDAY).get(0).getMin(), 59);
            assertEquals(departures.get(DayTypes.SATURDAY).get(52).getHour(), 22);
            assertEquals(departures.get(DayTypes.SATURDAY).get(52).getMin(), 47);

            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getHour(), 5);
            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getMin(), 20);
            assertEquals(departures.get(DayTypes.SUNDAY).get(49).getHour(), 22);
            assertEquals(departures.get(DayTypes.SUNDAY).get(49).getMin(), 47);

        } catch (IOException e) {
            fail();
        } catch (TimetableParseException e) {
            fail();
        }
    }


    @Test
    public void testTimetable2WithLegend() {
        String expectedStopName = "Borek Fałęcki";
        String expectedLegend = "Kurs do przystanku: Kombinat";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable2.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(parser.getStopName(), expectedStopName);

            Map<DayTypes, List<Departure>> departures = parser.parse(new Station(expectedStopName));

            assertEquals(departures.size(), 3);
            assertTrue(departures.containsKey(DayTypes.WEEKDAY));
            assertTrue(departures.containsKey(DayTypes.SATURDAY));
            assertTrue(departures.containsKey(DayTypes.SUNDAY));

            assertEquals(departures.get(DayTypes.WEEKDAY).size(), 62);
            assertEquals(departures.get(DayTypes.SATURDAY).size(), 54);
            assertEquals(departures.get(DayTypes.SUNDAY).size(), 51);

            assertEquals(departures.get(DayTypes.WEEKDAY).get(0).getHour(), 5);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(0).getMin(), 0);
            assertNull(departures.get(DayTypes.WEEKDAY).get(0).getExtraInfo());
            assertEquals(departures.get(DayTypes.WEEKDAY).get(61).getHour(), 23);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(61).getMin(), 36);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(61).getExtraInfo(), expectedLegend);

            assertEquals(departures.get(DayTypes.SATURDAY).get(0).getHour(), 5);
            assertNull(departures.get(DayTypes.SATURDAY).get(0).getExtraInfo());
            assertEquals(departures.get(DayTypes.SATURDAY).get(0).getMin(), 3);
            assertEquals(departures.get(DayTypes.SATURDAY).get(53).getHour(), 23);
            assertEquals(departures.get(DayTypes.SATURDAY).get(53).getMin(), 4);
            assertEquals(departures.get(DayTypes.SATURDAY).get(53).getExtraInfo(), expectedLegend);

            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getHour(), 5);
            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getMin(), 3);
            assertNull(departures.get(DayTypes.SUNDAY).get(0).getExtraInfo());
            assertEquals(departures.get(DayTypes.SUNDAY).get(50).getHour(), 23);
            assertEquals(departures.get(DayTypes.SUNDAY).get(50).getMin(), 6);
            assertEquals(departures.get(DayTypes.SUNDAY).get(50).getExtraInfo(), expectedLegend);

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


    @Test
    public void testTimetable3WithEverydayType() {
        String expectedStopName = "Mydlniki";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable3.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(parser.getStopName(), expectedStopName);

            Map<DayTypes, List<Departure>> departures = parser.parse(new Station(expectedStopName));

            assertEquals(departures.size(), 1);
            assertTrue(departures.containsKey(DayTypes.EVERYDAY));

            assertEquals(departures.get(DayTypes.EVERYDAY).size(), 6);

            assertEquals(departures.get(DayTypes.EVERYDAY).get(0).getHour(), 23);
            assertEquals(departures.get(DayTypes.EVERYDAY).get(0).getMin(), 30);
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
        String expectedLegend = "Kurs do przystanku: Dworzec Główny";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable4.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(parser.getStopName(), expectedStopName);

            Map<DayTypes, List<Departure>> departures = parser.parse(new Station(expectedStopName));

            assertEquals(departures.size(), 3);
            assertTrue(departures.containsKey(DayTypes.MONDAY_TO_THURSDAY));
            assertTrue(departures.containsKey(DayTypes.WEEKEND_NIGHTS));
            assertTrue(departures.containsKey(DayTypes.SUNDAY));

            assertEquals(departures.get(DayTypes.MONDAY_TO_THURSDAY).size(), 5);
            assertEquals(departures.get(DayTypes.WEEKEND_NIGHTS).size(), 5);
            assertEquals(departures.get(DayTypes.SUNDAY).size(), 5);

            assertEquals(departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0).getHour(), 23);
            assertEquals(departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0).getMin(), 32);
            assertEquals(departures.get(DayTypes.MONDAY_TO_THURSDAY).get(0).getExtraInfo(), expectedLegend);

            assertEquals(departures.get(DayTypes.WEEKEND_NIGHTS).get(1).getHour(), 0);
            assertEquals(departures.get(DayTypes.WEEKEND_NIGHTS).get(1).getMin(), 32);
            assertNull(departures.get(DayTypes.WEEKEND_NIGHTS).get(1).getExtraInfo());

            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getHour(), 23);
            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getMin(), 32);
            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getExtraInfo(), expectedLegend);

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


    @Test
    public void testTimetable5WithTwoLegendEntries() {
        String expectedStopName = "Złocień";
        String expectedLegend1 = "Kurs do przystanku: Wydział Farmaceutyczny UJ";
        String expectedLegend2 = "Kurs do przystanku: Prokocim Szpital";

        try {
            File inputFile = new File("./MPKParserTests/res/timetable5.html");

            TimetableParser parser = new TimetableParser(inputFile, "iso-8859-2");
            assertEquals(parser.getStopName(), expectedStopName);

            Map<DayTypes, List<Departure>> departures = parser.parse(new Station(expectedStopName));

            assertEquals(departures.size(), 3);
            assertTrue(departures.containsKey(DayTypes.WEEKDAY));
            assertTrue(departures.containsKey(DayTypes.SATURDAY));
            assertTrue(departures.containsKey(DayTypes.SUNDAY));

            assertEquals(departures.get(DayTypes.WEEKDAY).size(), 37);
            assertEquals(departures.get(DayTypes.SATURDAY).size(), 32);
            assertEquals(departures.get(DayTypes.SUNDAY).size(), 30);

            assertEquals(departures.get(DayTypes.WEEKDAY).get(0).getHour(), 4);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(0).getMin(), 41);
            assertNull(departures.get(DayTypes.WEEKDAY).get(0).getExtraInfo());

            assertEquals(departures.get(DayTypes.WEEKDAY).get(35).getHour(), 22);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(35).getMin(), 8);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(35).getExtraInfo(), expectedLegend1);

            assertEquals(departures.get(DayTypes.WEEKDAY).get(36).getHour(), 23);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(36).getMin(), 5);
            assertEquals(departures.get(DayTypes.WEEKDAY).get(36).getExtraInfo(), expectedLegend2);

            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getHour(), 4);
            assertEquals(departures.get(DayTypes.SUNDAY).get(0).getMin(), 40);
            assertNull(departures.get(DayTypes.SUNDAY).get(0).getExtraInfo());

            assertEquals(departures.get(DayTypes.SUNDAY).get(28).getHour(), 22);
            assertEquals(departures.get(DayTypes.SUNDAY).get(28).getMin(), 49);
            assertEquals(departures.get(DayTypes.SUNDAY).get(28).getExtraInfo(), expectedLegend1);

            assertEquals(departures.get(DayTypes.SUNDAY).get(29).getHour(), 23);
            assertEquals(departures.get(DayTypes.SUNDAY).get(29).getMin(), 35);
            assertEquals(departures.get(DayTypes.SUNDAY).get(29).getExtraInfo(), expectedLegend2);


        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        }
    }


}
