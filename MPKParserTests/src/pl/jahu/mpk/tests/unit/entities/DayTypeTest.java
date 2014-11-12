package pl.jahu.mpk.tests.unit.entities;

import org.junit.Test;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.utils.TimeUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-12.
 */
public class DayTypeTest {

    /******************** TESTS ********************/

    @Test
    public void mondayTest() {
        DayType mondayDayType = buildDayType(new int[]{Calendar.MONDAY});
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
    public void weekendTest() {
        DayType weekendDayType = buildDayType(new int[]{Calendar.SATURDAY, Calendar.SUNDAY});
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
    public void allWeekTest() {
        DayType allWeekDayType = buildDayType(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY});
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
    public void datesPeriodTest() {
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
    public void singleDateTest() {
        DayType dayType = new DayType(TimeUtils.buildDate(24, 12, 2014));
        assertTrue(dayType.matches(TimeUtils.buildDate(24, 12, 2014)));

        assertFalse(dayType.matches(TimeUtils.buildDate(23, 12, 2014)));
        assertFalse(dayType.matches(TimeUtils.buildDate(25, 12, 2014)));
    }


    /***************** API ********************/


    private DayType buildDayType(int[] expectedDaysOfWeek) {
        boolean[] expectedValues = new boolean[8];
        for (int day : expectedDaysOfWeek) {
            expectedValues[day] = true;
        }
        Map<Integer, Boolean> daysOfWeek = new HashMap<>();
        daysOfWeek.put(Calendar.MONDAY, expectedValues[Calendar.MONDAY]);
        daysOfWeek.put(Calendar.TUESDAY, expectedValues[Calendar.TUESDAY]);
        daysOfWeek.put(Calendar.WEDNESDAY, expectedValues[Calendar.WEDNESDAY]);
        daysOfWeek.put(Calendar.THURSDAY, expectedValues[Calendar.THURSDAY]);
        daysOfWeek.put(Calendar.FRIDAY, expectedValues[Calendar.FRIDAY]);
        daysOfWeek.put(Calendar.SATURDAY, expectedValues[Calendar.SATURDAY]);
        daysOfWeek.put(Calendar.SUNDAY, expectedValues[Calendar.SUNDAY]);
        return new DayType(daysOfWeek, false);
    }

}
