package pl.jahu.mpk.utils;

import org.joda.time.DateTime;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

// TODO Handle WEEKEND_NIGHTS properly

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtilsTest {

    @Test
    public void moreLessEqual_test1() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 25);
        assertTrue(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void moreLessEqual_test2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 26);
        assertTrue(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void moreLessEqual_test3() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 27);
        Time time2 = new Time(13, 25);
        assertTrue(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void moreLessEqual_test4() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Time time1 = new Time(13, 25);
        Time time2 = new Time(13, 28);
        assertFalse(TimeUtils.moreLessEqual(time1, time2));
    }

    @Test
    public void buildDate_withTime() {
        DateTime date = TimeUtils.buildDate(14, 03, 13, 07, 1995);

        assertEquals(14, date.getHourOfDay());
        assertEquals(3, date.getMinuteOfHour());
        assertEquals(13, date.getDayOfMonth());
        assertEquals(7, date.getMonthOfYear());
        assertEquals(1995, date.getYear());
    }

    @Test
    public void buildDate_defaultTime() {
        DateTime date = TimeUtils.buildDate(13, 07, 1995);

        assertEquals(12, date.getHourOfDay());
        assertEquals(0, date.getMinuteOfHour());
    }

    @Test
    public void previousDay_test1() {
        assertEquals(4, TimeUtils.previousDay(5));
    }

    @Test
    public void previousDay_test2() {
        assertEquals(7, TimeUtils.previousDay(1));
    }

}
