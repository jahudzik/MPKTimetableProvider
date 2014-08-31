package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.parser.utils.Time;
import static org.junit.Assert.assertEquals;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-30.
 */
public class TimeTest {

    @Test
    public void testCompare1() {
        Time time1 = new Time(5, 10);
        Time time2 = new Time(5, 20);
        assertEquals(time1.compareTo(time2), -10);
        assertEquals(time2.compareTo(time1), 10);
    }

    @Test
    public void testCompare2() {
        Time time1 = new Time(15, 45);
        Time time2 = new Time(16, 5);
        assertEquals(time1.compareTo(time2), -20);
        assertEquals(time2.compareTo(time1), 20);
    }

    @Test
    public void testCompare3() {
        Time time1 = new Time(12, 45);
        Time time2 = new Time(12, 45);
        assertEquals(time1.compareTo(time2), 0);
    }

    @Test
    public void testCompare4() {
        Time time1 = new Time(23, 50);
        Time time2 = new Time(0, 5);
        assertEquals(time1.compareTo(time2), -15);
        assertEquals(time2.compareTo(time1), 15);
    }

    @Test
    public void testCompare5() {
        Time time1 = new Time(12, 05);
        Time time2 = new Time(0, 0);
        assertEquals(time1.compareTo(time2), -715);
        assertEquals(time2.compareTo(time1), 715);
    }


    @Test
    public void testToString1() {
        Time time1 = new Time(4, 5);
        assertEquals(time1.toString(), "04:05");
    }

}
