package pl.jahu.mpk.parsers;

import org.junit.Test;
import pl.jahu.mpk.entities.DayType;
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
    public void checkDaysOfWeek_monday() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Poniedziałek", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_tuesday() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Wtorek", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.TUESDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_wednesday() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Środa", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.WEDNESDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_thursday() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Czwartek", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.THURSDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_friday() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Piątek", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.FRIDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_saturday1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Sobota", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_saturday2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Soboty", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_sunday1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Niedziela", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SUNDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_sunday2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Święta", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SUNDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_weekdays1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Dni powszednie", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_weekdays2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Dzień powszedni", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_weekend() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Weekend", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_allWeek() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Wszystkie dni tygodnia", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_weekPeriod1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pon.-Czw.", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_weekPeriod2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Sob.-Nie.", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
     public void checkDaysOfWeek_weekPeriod3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Nie.-Pon.", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_weekPeriod4() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Czw.-Pon.", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_night1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pt/Sob.", true, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.FRIDAY}, true);
    }

    @Test
    public void checkDaysOfWeek_night2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Sob./Św.", true, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.SATURDAY}, true);
    }

    @Test
    public void checkDaysOfWeek_night3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pn/Wto", true, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY}, true);
    }

    @Test
    public void checkDaysOfWeek_allWeekNight() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Wszystkie dni tygodnia", true, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, true);
    }


    @Test
    public void checkDaysOfWeek_merge1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Pt/Sob.,Sob./Św.", true, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.FRIDAY, Calendar.SATURDAY}, true);
    }

    @Test
    public void checkDaysOfWeek_merge2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Poniedziałek,Wtorek", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY}, false);
    }

    @Test
    public void checkDaysOfWeek_merge3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("Poniedziałek, Wtorek", false, null);
        checkDaysOfWeek(dayType, new int[]{Calendar.MONDAY, Calendar.TUESDAY}, false);
    }



    @Test
    public void checkDates_singleDate1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("01.05.2015", false, null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2015), false);
    }

    @Test
    public void checkDates_singleDate2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("24.12.2014", false, null);
        checkDates(dayType, TimeUtils.buildDate(24, 12, 2014), false);
    }

    @Test
    public void checkDates_datesPeriod1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("25-26.12.2014", false, null);
        checkDates(dayType, TimeUtils.buildDate(25, 12, 2014), TimeUtils.buildDate(26, 12, 2014), false);
    }

    @Test
    public void checkDates_datesPeriod2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("1-3.05.2015", false, null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2015), TimeUtils.buildDate(3, 5, 2015), false);
    }

    @Test
    public void checkDates_datesPeriod3() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("01-03.05.2015", false, null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2015), TimeUtils.buildDate(3, 5, 2015), false);
    }

    @Test
    public void checkDates_defaultDateYear1() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("1-3.05", false, null);
        checkDates(dayType, TimeUtils.buildDate(1, 5, 2014), TimeUtils.buildDate(3, 5, 2014), false);
    }

    @Test
    public void checkDates_defaultDateYear2() throws TimetableParseException {
        DayType dayType = DayTypeParser.parse("24.12", false, null);
        checkDates(dayType, TimeUtils.buildDate(24, 12, 2014), false);
    }


    @Test(expected = TimetableParseException.class)
    public void checkDates_unsupportedDayType1() throws TimetableParseException {
        DayTypeParser.parse("29.04-02.05.2015", false, null);
    }

    @Test(expected = TimetableParseException.class)
    public void checkDates_unsupportedDayType2() throws TimetableParseException {
        DayTypeParser.parse("Wigilia", false, null);
    }

    /***************** API ********************/

    private void checkDaysOfWeek(DayType dayType, int[] expectedDaysOfWeek, boolean expectedNigtly) {
        boolean[] expectedValues = new boolean[8];
        for (int day : expectedDaysOfWeek) {
            expectedValues[day] = true;
        }
        Map<Integer, Boolean> daysOfWeek = dayType.getDaysOfWeek();
        assertEquals(dayType.toString(), expectedValues[Calendar.MONDAY], daysOfWeek.get(Calendar.MONDAY));
        assertEquals(dayType.toString(), expectedValues[Calendar.TUESDAY], daysOfWeek.get(Calendar.TUESDAY));
        assertEquals(dayType.toString(), expectedValues[Calendar.WEDNESDAY], daysOfWeek.get(Calendar.WEDNESDAY));
        assertEquals(dayType.toString(), expectedValues[Calendar.THURSDAY], daysOfWeek.get(Calendar.THURSDAY));
        assertEquals(dayType.toString(), expectedValues[Calendar.FRIDAY], daysOfWeek.get(Calendar.FRIDAY));
        assertEquals(dayType.toString(), expectedValues[Calendar.SATURDAY], daysOfWeek.get(Calendar.SATURDAY));
        assertEquals(dayType.toString(), expectedValues[Calendar.SUNDAY], daysOfWeek.get(Calendar.SUNDAY));
        assertEquals(expectedNigtly, dayType.isNightly());
        assertNull(dayType.getStartDate());
        assertNull(dayType.getEndDate());
    }

    private void checkDates(DayType dayType, Date expectedDate, boolean expectedNigtly) {
        checkDates(dayType, expectedDate, expectedDate, expectedNigtly);
    }

    private void checkDates(DayType dayType, Date expectedStartDate, Date expectedEndDate, boolean expectedNigtly) {
        assertEquals(expectedStartDate, dayType.getStartDate());
        assertEquals(expectedEndDate, dayType.getEndDate());
        assertEquals(expectedNigtly, dayType.isNightly());
        assertNull(dayType.getDaysOfWeek());
    }

}
