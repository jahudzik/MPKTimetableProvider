package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Test;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.parsers.DayTypeParser;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-07.
 */
public class DayTypeParserTest {


    /******************** TESTS ********************/

    @Test
    public void mondayTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Poniedziałek", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY}, false);
    }

    @Test
    public void tuesdayTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Wtorek", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.TUESDAY}, false);
    }

    @Test
    public void wednesdayTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Środa", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.WEDNESDAY}, false);
    }

    @Test
    public void thursdayTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Czwartek", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.THURSDAY}, false);
    }

    @Test
    public void fridayTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Piątek", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.FRIDAY}, false);
    }

    @Test
    public void saturdayTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Sobota", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY}, false);
    }

    @Test
    public void saturdayTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Soboty", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY}, false);
    }

    @Test
    public void sundayTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Niedziela", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SUNDAY}, false);
    }

    @Test
    public void sundayTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Święta", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SUNDAY}, false);
    }

    @Test
    public void weekdaysTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Dni powszednie", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY}, false);
    }

    @Test
    public void weekdaysTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Dzień powszedni", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY}, false);
    }

    @Test
    public void weekendTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Weekend", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void allWeekTest() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Wszystkie dni tygodnia", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void weekPeriodTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pon.-Czw.", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY}, false);
    }

    @Test
    public void weekPeriodTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Sob.-Nie.", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
     public void weekPeriodTest3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Nie.-Pon.", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void weekPeriodTest4() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Czw.-Pon.", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void nightTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pt/Sob.", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.FRIDAY, Calendar.SATURDAY}, true);
    }

    @Test
    public void nightTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Sob./Św.", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, true);
    }

    @Test
    public void nightTest3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pn/Wto", null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY}, true);
    }



    @Test
    public void singleDateTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("01.05.2015", null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2015));
    }

    @Test
    public void singleDateTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("24.12.2014", null);
        checkDates(dayType, TimeUtils.buildDate(24, 12, 2014));
    }

    @Test
    public void datesPeriodTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("25-26.12.2014", null);
        checkDates(dayType, TimeUtils.buildDate(25, 12, 2014), TimeUtils.buildDate(26, 12, 2014));
    }

    @Test
    public void datesPeriodTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("1-3.05.2015", null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2015), TimeUtils.buildDate(3, 5, 2015));
    }

    @Test
    public void datesPeriodTest3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("01-03.05.2015", null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2015), TimeUtils.buildDate(3, 5, 2015));
    }

    @Test
    public void defaultDateYearTest1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("1-3.05", null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2014), TimeUtils.buildDate(3, 5, 2014));
    }

    @Test
    public void defaultDateYearTest2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("24.12", null);
        checkDates(dayType, TimeUtils.buildDate(24, 12, 2014));
    }


    @Test(expected = TimetableParseException.class)
    public void unsupportedDayTypeTest1() throws TimetableParseException {
        DayTypeParser.parse("29.04-02.05.2015", null);
    }

    @Test(expected = TimetableParseException.class)
    public void unsupportedDayTypeTest2() throws TimetableParseException {
        DayTypeParser.parse("Wigilia", null);
    }

    /***************** API ********************/

    private void checkDaysOfWeek(DayType dayType, int[] expectedDaysOfWeek, boolean expectedNigtly) {
        boolean[] expectedValues = new boolean[8];
        for (int day : expectedDaysOfWeek) {
            expectedValues[day] = true;
        }
        Map<Integer, Boolean> daysOfWeek = dayType.getDaysOfWeek();
        assertEquals(expectedValues[Calendar.MONDAY], daysOfWeek.get(Calendar.MONDAY));
        assertEquals(expectedValues[Calendar.TUESDAY], daysOfWeek.get(Calendar.TUESDAY));
        assertEquals(expectedValues[Calendar.WEDNESDAY], daysOfWeek.get(Calendar.WEDNESDAY));
        assertEquals(expectedValues[Calendar.THURSDAY], daysOfWeek.get(Calendar.THURSDAY));
        assertEquals(expectedValues[Calendar.FRIDAY], daysOfWeek.get(Calendar.FRIDAY));
        assertEquals(expectedValues[Calendar.SATURDAY], daysOfWeek.get(Calendar.SATURDAY));
        assertEquals(expectedValues[Calendar.SUNDAY], daysOfWeek.get(Calendar.SUNDAY));
        assertEquals(expectedNigtly, dayType.isNightly());
        assertNull(dayType.getStartDate());
        assertNull(dayType.getEndDate());
    }

    private void checkDates(DayType dayType, Date expectedDate) {
        checkDates(dayType, expectedDate, expectedDate);
    }

    private void checkDates(DayType dayType, Date expectedStartDate, Date expectedEndDate) {
        assertEquals(expectedStartDate, dayType.getStartDate());
        assertEquals(expectedEndDate, dayType.getEndDate());
        assertNull(dayType.getDaysOfWeek());
    }

}
