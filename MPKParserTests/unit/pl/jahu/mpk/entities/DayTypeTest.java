package pl.jahu.mpk.entities;

import org.joda.time.DateTimeConstants;
import org.junit.Test;
import pl.jahu.mpk.utils.TimeUtils;

import static org.junit.Assert.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-12.
 */
public class DayTypeTest {

    /******************** TESTS ********************/

    @Test
    public void matches_test1() {
        DayType mondayDayType = DayType.getInstance(new int[]{DateTimeConstants.MONDAY}, false);
        assertTrue(mondayDayType.matches(TimeUtils.buildDate(10, 11, 2014)));
        assertTrue(mondayDayType.matches(TimeUtils.buildDate(17, 11, 2014)));
        assertTrue(mondayDayType.matches(TimeUtils.buildDate( 4,  5, 2015)));

        assertFalse(mondayDayType.matches(TimeUtils.buildDate(11, 11, 2014)));
        assertFalse(mondayDayType.matches(TimeUtils.buildDate(12, 11, 2014)));
        assertFalse(mondayDayType.matches(TimeUtils.buildDate(13, 11, 2014)));
        assertFalse(mondayDayType.matches(TimeUtils.buildDate(14, 11, 2014)));
        assertFalse(mondayDayType.matches(TimeUtils.buildDate(15, 11, 2014)));
        assertFalse(mondayDayType.matches(TimeUtils.buildDate(16, 11, 2014)));
    }

    @Test
    public void matches_test2() {
        DayType weekendDayType = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, false);
        assertTrue(weekendDayType.matches(TimeUtils.buildDate(15, 11, 2014)));
        assertTrue(weekendDayType.matches(TimeUtils.buildDate(16, 11, 2014)));
        assertTrue(weekendDayType.matches(TimeUtils.buildDate(29, 11, 2014)));
        assertTrue(weekendDayType.matches(TimeUtils.buildDate(30, 11, 2014)));

        assertFalse(weekendDayType.matches(TimeUtils.buildDate(10, 11, 2014)));
        assertFalse(weekendDayType.matches(TimeUtils.buildDate(11, 11, 2014)));
        assertFalse(weekendDayType.matches(TimeUtils.buildDate(12, 11, 2014)));
        assertFalse(weekendDayType.matches(TimeUtils.buildDate(13, 11, 2014)));
        assertFalse(weekendDayType.matches(TimeUtils.buildDate(14, 11, 2014)));
    }

    @Test
    public void matches_test3() {
        DayType allWeekDayType = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, false);
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(10, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(11, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(12, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(13, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(14, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(15, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(16, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(17, 11, 2014)));
        assertTrue(allWeekDayType.matches(TimeUtils.buildDate(18, 11, 2014)));
    }

    @Test
    public void matches_test4() {
        DayType dayType = new DayType(TimeUtils.buildDate(1, 5, 2014), TimeUtils.buildDate(3, 5, 2014));
        assertTrue(dayType.matches(TimeUtils.buildDate(1, 5, 2014)));
        assertTrue(dayType.matches(TimeUtils.buildDate(2, 5, 2014)));
        assertTrue(dayType.matches(TimeUtils.buildDate(3, 5, 2014)));

        assertFalse(dayType.matches(TimeUtils.buildDate(30, 4, 2014)));
        assertFalse(dayType.matches(TimeUtils.buildDate(4, 5, 2014)));
        assertFalse(dayType.matches(TimeUtils.buildDate(2, 6, 2014)));
        assertFalse(dayType.matches(TimeUtils.buildDate(2, 5, 2015)));
    }

    @Test
    public void matches_test5() {
        DayType dayType = new DayType(TimeUtils.buildDate(24, 12, 2014));
        assertTrue(dayType.matches(TimeUtils.buildDate(24, 12, 2014)));

        assertFalse(dayType.matches(TimeUtils.buildDate(23, 12, 2014)));
        assertFalse(dayType.matches(TimeUtils.buildDate(25, 12, 2014)));
    }

    @Test
    public void matches_test6() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, false);
        // matching with Friday 00:15
        assertTrue(dayType.matches(TimeUtils.buildDate(0, 15, 14, 11, 2014)));
        // matching with Friday 03:15
        assertFalse(dayType.matches(TimeUtils.buildDate(3, 15, 14, 11, 2014)));
    }

    @Test
    public void matches_test7() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, false);
        // matching with Thursday 01:15
        assertFalse(dayType.matches(TimeUtils.buildDate(1, 15, 13, 11, 2014)));
        // matching with Thursday 12:15
        assertTrue(dayType.matches(TimeUtils.buildDate(12, 15, 13, 11, 2014)));
    }

    @Test
    public void matchesNight_test1() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, true);
        // matching with Thursday 23:15
        assertTrue(dayType.matches(TimeUtils.buildDate(23, 15, 13, 11, 2014)));
    }

    @Test
    public void matchesNight_test2() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, true);
        // matching with Friday 03:15
        assertTrue(dayType.matches(TimeUtils.buildDate(3, 15, 14, 11, 2014)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectMatchingTime_test1() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, true);
        // matching nightly with 07:15
        assertFalse(dayType.matches(TimeUtils.buildDate(7, 15, 14, 11, 2014)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectMatchingTime_test2() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, false);
        // matching daily with 02:15
        assertFalse(dayType.matches(TimeUtils.buildDate(2, 15, 14, 11, 2014)));
    }


    @Test
    public void toString_test1() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.MONDAY}, false);
        assertEquals("[daily:Mon]", dayType.toString());
    }

    @Test
    public void toString_test2() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, false);
        assertEquals("[daily:Sat|Sun]", dayType.toString());
    }

    @Test
    public void toString_test3() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, false);
        assertEquals("[daily:Mon|Tue|Wed|Thu|Fri|Sat|Sun]", dayType.toString());
    }

    @Test
    public void toString_test4() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.MONDAY}, true);
        assertEquals("[nightly:Mon]", dayType.toString());
    }

    @Test
    public void toString_test5() {
        DayType dayType = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, true);
        assertEquals("[nightly:Sat|Sun]", dayType.toString());
    }

    @Test
    public void toString_test6() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertEquals("[05.04.2014-05.04.2014]", dayType.toString());
    }

    @Test
    public void toString_test7() {
        DayType dayType = new DayType(TimeUtils.buildDate(25, 12, 2014), TimeUtils.buildDate(26, 12, 2014));
        assertEquals("[25.12.2014-26.12.2014]", dayType.toString());
    }


    @Test
    public void equals_test1() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertTrue(dayType1.equals(dayType2));
        assertTrue(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test2() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(6, 4, 2014));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test3() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 5, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test4() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2015));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test5() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2015), TimeUtils.buildDate(7, 4, 2015));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2015), TimeUtils.buildDate(7, 4, 2015));
        assertTrue(dayType1.equals(dayType2));
        assertTrue(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test6() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014), TimeUtils.buildDate(8, 4, 2015));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2015), TimeUtils.buildDate(7, 4, 2015));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test7() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY}, false);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY}, false);
        assertTrue(dayType1.equals(dayType2));
        assertTrue(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test8() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY}, false);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.SUNDAY}, false);
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test9() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, false);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY}, false);
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test10() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.THURSDAY}, false);
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equals_test11() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertTrue(dayType.equals(dayType));
    }

    @Test
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equals_test12() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        LineNumber lineNumber = new LineNumber(16);
        assertFalse(dayType.equals(lineNumber));
    }

    @Test
    public void equals_test13() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertFalse(dayType.equals(null));
    }

    @Test
    public void hashCode_test1() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test2() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(6, 4, 2014));
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test3() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY}, true);
        assertEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test4() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY}, false);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test5() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY}, true);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test6() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY}, true);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test7() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY}, true);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCode_test8() {
        DayType dayType1 = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY}, true);
        assertEquals(dayType1.hashCode(), dayType2.hashCode());
    }

}
