package pl.jahu.mpk.tests.unit.entities;

import org.junit.Test;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.utils.TimeUtils;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-12.
 */
public class DayTypeTest {

    /******************** TESTS ********************/

    @Test
    public void matchesTest1() {
        DayType mondayDayType = DayType.getInstance(new int[]{Calendar.MONDAY}, false);
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
    public void matchesTest2() {
        DayType weekendDayType = DayType.getInstance(new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, false);
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
    public void matchesTest3() {
        DayType allWeekDayType = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
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
    public void matchesTest4() {
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
    public void matchesTest5() {
        DayType dayType = new DayType(TimeUtils.buildDate(24, 12, 2014));
        assertTrue(dayType.matches(TimeUtils.buildDate(24, 12, 2014)));

        assertFalse(dayType.matches(TimeUtils.buildDate(23, 12, 2014)));
        assertFalse(dayType.matches(TimeUtils.buildDate(25, 12, 2014)));
    }


    @Test
    public void toStringTest1() {
        DayType dayType = DayType.getInstance(new int[]{Calendar.MONDAY}, false);
        assertEquals("[daily:Mon]", dayType.toString());
    }

    @Test
    public void toStringTest2() {
        DayType dayType = DayType.getInstance(new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, false);
        assertEquals("[daily:Sat|Sun]", dayType.toString());
    }

    @Test
    public void toStringTest3() {
        DayType dayType = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
        assertEquals("[daily:Mon|Tue|Wed|Thu|Fri|Sat|Sun]", dayType.toString());
    }

    @Test
    public void toStringTest4() {
        DayType dayType = DayType.getInstance(new int[]{Calendar.MONDAY}, true);
        assertEquals("[nightly:Mon]", dayType.toString());
    }

    @Test
    public void toStringTest5() {
        DayType dayType = DayType.getInstance(new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, true);
        assertEquals("[nightly:Sat|Sun]", dayType.toString());
    }

    @Test
    public void toStringTest6() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertEquals("[5.4.2014-5.4.2014]", dayType.toString());
    }

    @Test
    public void toStringTest7() {
        DayType dayType = new DayType(TimeUtils.buildDate(25, 12, 2014), TimeUtils.buildDate(26, 12, 2014));
        assertEquals("[25.12.2014-26.12.2014]", dayType.toString());
    }


    @Test
    public void equalsTest1() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertTrue(dayType1.equals(dayType2));
        assertTrue(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest2() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(6, 4, 2014));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest3() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 5, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest4() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2015));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest5() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2015), TimeUtils.buildDate(7, 4, 2015));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2015), TimeUtils.buildDate(7, 4, 2015));
        assertTrue(dayType1.equals(dayType2));
        assertTrue(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest6() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014), TimeUtils.buildDate(8, 4, 2015));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2015), TimeUtils.buildDate(7, 4, 2015));
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest7() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.SATURDAY}, false);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.SATURDAY}, false);
        assertTrue(dayType1.equals(dayType2));
        assertTrue(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest8() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.SATURDAY}, false);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.SUNDAY}, false);
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest9() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, false);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY}, false);
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest10() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.THURSDAY}, false);
        assertFalse(dayType1.equals(dayType2));
        assertFalse(dayType2.equals(dayType1));
    }

    @Test
    public void equalsTest11() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertTrue(dayType.equals(dayType));
    }

    @Test
    public void equalsTest12() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        LineNumber lineNumber = new LineNumber(16);
        assertFalse(dayType.equals(lineNumber));
    }

    @Test
    public void equalsTest13() {
        DayType dayType = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertFalse(dayType.equals(null));
    }

    @Test
    public void hashCodeTest1() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        assertEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest2() {
        DayType dayType1 = new DayType(TimeUtils.buildDate(5, 4, 2014));
        DayType dayType2 = new DayType(TimeUtils.buildDate(6, 4, 2014));
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest3() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.FRIDAY}, true);
        assertEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest4() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.FRIDAY}, false);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest5() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.FRIDAY, Calendar.SATURDAY}, true);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest6() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.FRIDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.SATURDAY}, true);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest7() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.SATURDAY, Calendar.SUNDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.FRIDAY, Calendar.SATURDAY}, true);
        assertNotEquals(dayType1.hashCode(), dayType2.hashCode());
    }

    @Test
    public void hashCodeTest8() {
        DayType dayType1 = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY}, true);
        DayType dayType2 = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY}, true);
        assertEquals(dayType1.hashCode(), dayType2.hashCode());
    }

}
