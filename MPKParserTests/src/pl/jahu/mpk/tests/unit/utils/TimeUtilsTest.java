package pl.jahu.mpk.tests.unit.utils;

import org.junit.Test;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parsers.exceptions.UnsupportedDayTypesConfigurationException;
import pl.jahu.mpk.utils.Time;
import pl.jahu.mpk.utils.TimeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;

import static org.junit.Assert.*;

// TODO Handle WEEKEND_NIGHTS properly

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtilsTest {


    @Test
    public void testSunday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.SUNDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.SUNDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.SUNDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.SUNDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.SUNDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.SUNDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test
    public void testMonday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.MONDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.MONDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.MONDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.MONDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.MONDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.MONDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test
    public void testThursday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.THURSDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.THURSDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.THURSDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.THURSDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.THURSDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.THURSDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test
    public void testFriday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.FRIDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.FRIDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }


    @Test
    public void testSaturday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.SATURDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.SATURDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.SATURDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.SATURDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.SATURDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.SATURDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test(expected = UnsupportedDayTypesConfigurationException.class)
    public void testNull() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(null, Calendar.SATURDAY));
    }


    private boolean testMoreLessEqual(Time time1, Time time2) {
        try {
            Method method = TimeUtils.class.getDeclaredMethod("moreLessEqual", Time.class, Time.class);
            method.setAccessible(true);
            return (Boolean) method.invoke(null, time1, time2);
        } catch (NoSuchMethodException e) {
            fail();
            return false;
        } catch (InvocationTargetException e) {
            fail();
            return false;
        } catch (IllegalAccessException e) {
            fail();
            return false;
        }
    }

    @Test
    public void testMoreLessEqual1() {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 25);
        assertTrue(testMoreLessEqual(time1, time2));
    }

    @Test
    public void testMoreLessEqual2() {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 26);
        assertTrue(testMoreLessEqual(time1, time2));
    }

    @Test
    public void testMoreLessEqual3() {
        Time time1 = new Time(13, 27);
        Time time2 = new Time(13, 25);
        assertTrue(testMoreLessEqual(time1, time2));
    }

    @Test
    public void testMoreLessEqual4() {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 28);
        assertFalse(testMoreLessEqual(time1, time2));
    }

}
