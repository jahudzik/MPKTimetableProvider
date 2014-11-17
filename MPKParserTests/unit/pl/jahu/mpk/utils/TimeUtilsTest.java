package pl.jahu.mpk.utils;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        Date date = TimeUtils.buildDate(14, 03, 13, 07, 1995);

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(14, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(3, cal.get(Calendar.MINUTE));
        assertEquals(13, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(6, cal.get(Calendar.MONTH));
        assertEquals(1995, cal.get(Calendar.YEAR));
    }

    @Test
    public void buildDate_defaultTime() {
        Date date = TimeUtils.buildDate(13, 07, 1995);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(12, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, cal.get(Calendar.MINUTE));
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
