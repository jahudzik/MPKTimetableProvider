package pl.jahu.mpk.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-30.
 */
public class TimeTest {

    /******************** TESTS ********************/

    @Test
    public void compareTo_test1() {
        checkTimeDifference(new Time(05, 10), new Time(05, 20), -10, 10);
    }

    @Test
    public void compareTo_test2() {
        checkTimeDifference(new Time(15, 45), new Time(16, 05), -20, 20);
    }

    @Test
    public void compareTo_test3() {
        checkTimeDifference(new Time(12, 45), new Time(12, 45), 0, 0);
    }

    @Test
    public void compareTo_test4() {
        checkTimeDifference(new Time(23, 50), new Time(0, 05), -15, 15);
    }

    @Test
    public void compareTo_test5() {
        checkTimeDifference(new Time(12, 05), new Time(0, 0), -715, 715);
    }

    @Test
    public void compareTo_testDaytime() {
        checkDaytimeDifference(new Time(23, 50), new Time(0, 05), -15, 15);
    }


    @Test
    public void toString_test() {
        Time time1 = new Time(4, 5);
        assertEquals(time1.toString(), "04:05");
    }

    /******************** API ********************/


    private void checkTimeDifference(Time time1, Time time2, int expectedDiff1, int expectedDiff2) {
        assertEquals(time1.compareTo(time2), expectedDiff1);
        assertEquals(time2.compareTo(time1), expectedDiff2);
    }

    private void checkDaytimeDifference(Time time1, Time time2, int expectedDiff1, int expectedDiff2) {
        assertEquals(time1.compareDaytimeTo(time2), expectedDiff1);
        assertEquals(time2.compareDaytimeTo(time1), expectedDiff2);
    }

}
