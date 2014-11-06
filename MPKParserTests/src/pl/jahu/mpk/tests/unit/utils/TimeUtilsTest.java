package pl.jahu.mpk.tests.unit.utils;

import org.junit.Test;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parsers.exceptions.UnsupportedDayTypesConfigurationException;
import pl.jahu.mpk.utils.Time;
import pl.jahu.mpk.utils.TimeUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// TODO Handle WEEKEND_NIGHTS properly

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtilsTest {


    @Test
    public void testSunday() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.SUNDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.SUNDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.SUNDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.SUNDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.SUNDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.SUNDAY));
    }

    @Test
    public void testMonday() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.MONDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.MONDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.MONDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.MONDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.MONDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.MONDAY));
    }

    @Test
    public void testThursday() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.THURSDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.THURSDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.THURSDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.THURSDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.THURSDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.THURSDAY));
    }

    @Test
    public void testFriday() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.FRIDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.FRIDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.FRIDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.FRIDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.FRIDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.FRIDAY));
    }


    @Test
    public void testSaturday() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.SATURDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.SATURDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.SATURDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.SATURDAY));
        assertFalse(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.SATURDAY));
        assertTrue(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.SATURDAY));
    }

    @Test(expected = UnsupportedDayTypesConfigurationException.class)
    public void testNull() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(null, Calendar.SATURDAY));
    }


    @Test
    public void testMoreLessEqual1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 25);
        assertTrue(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void testMoreLessEqual2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 26);
        assertTrue(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void testMoreLessEqual3() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 27);
        Time time2 = new Time(13, 25);
        assertTrue(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void testMoreLessEqual4() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 28);
        assertFalse(TimeUtils.moreLessEqual(time1, time2));
    }

}
