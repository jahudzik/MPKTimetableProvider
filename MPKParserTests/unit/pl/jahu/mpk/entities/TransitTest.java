package pl.jahu.mpk.entities;

import org.junit.Test;
import pl.jahu.mpk.TestUtils;
import pl.jahu.mpk.utils.Time;

import static org.junit.Assert.assertEquals;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class TransitTest {

    /******************** TESTS ********************/

    @Test
    public void constructor_test() {
        Transit transit = new Transit(new Line(123, TestUtils.EXAMPLE_LINE_TYPE));
        checkStopsList(transit, new TransitStop[]{});
    }


    @Test
    public void addStop_test() {
        Transit transit = new Transit(new Line(123, TestUtils.EXAMPLE_LINE_TYPE));
        TransitStop stop1 = new TransitStop(new Time(15, 20), "station1");
        TransitStop stop2 = new TransitStop(new Time(15, 30), "station2");
        TransitStop stop3 = new TransitStop(new Time(15, 25), "station3");

        transit.addStop(stop1);
        checkStopsList(transit, new TransitStop[]{stop1});

        transit.addStop(stop2);
        checkStopsList(transit, new TransitStop[]{stop1, stop2});

        transit.addStop(stop3);
        checkStopsList(transit, new TransitStop[]{stop1, stop3, stop2});
    }

    @Test
    public void getDirections_test() {
        Transit transit = new Transit(new Line(1, TestUtils.EXAMPLE_LINE_TYPE));
        assertEquals("[empty]", transit.getDirections());
    }

    @Test
    public void toString_test1() {
        Transit transit = new Transit(new Line(1, TestUtils.EXAMPLE_LINE_TYPE));
        assertEquals("[empty]", transit.toString());
    }

    @Test
    public void toString_test2() {
        Transit transit = new Transit(new Line(1, TestUtils.EXAMPLE_LINE_TYPE));
        transit.addStop(new TransitStop(new Time(10, 13), "station1"));
        transit.addStop(new TransitStop(new Time(10, 16), "station2"));
        transit.addStop(new TransitStop(new Time(10, 18), "station3"));
        transit.setDestStation("station4");

        assertEquals("station1 [10:13] station2 [10:16] station3 [10:18] -> station4", transit.toString());
    }

    /******************* API ********************/

    private void checkStopsList(Transit transit, TransitStop[] expectedStops) {
        assertEquals(expectedStops.length, transit.getStops().size());
        for (int i = 0; i < expectedStops.length; i++) {
            assertEquals(expectedStops[i], transit.getStops().get(i));
        }
    }

}
