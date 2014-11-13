package pl.jahu.mpk.tests.unit.utils;

import org.junit.Test;
import pl.jahu.mpk.utils.Time;
import pl.jahu.mpk.utils.TimeUtils;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

// TODO Handle WEEKEND_NIGHTS properly

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtilsTest {

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
