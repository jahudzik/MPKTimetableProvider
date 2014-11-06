package pl.jahu.mpk.tests.unit.entities;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Transit;
import pl.jahu.mpk.entities.TransitStop;
import pl.jahu.mpk.utils.Time;

import static org.junit.Assert.assertEquals;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class TransitTest {

    /******************** TESTS ********************/

    @Test
    public void testInitialization() {
        Transit transit = new Transit(new LineNumber(123));
        checkStopsList(transit, new TransitStop[]{});
    }


    @Test
    public void testAddingStops() {
        Transit transit = new Transit(new LineNumber(123));
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
    public void getDirectionsTest() {
        Transit transit = new Transit(new LineNumber(1));
        assertEquals("[empty]", transit.getDirections());
    }

    @Test
    public void toStringTest1() {
        Transit transit = new Transit(new LineNumber(1));
        assertEquals("[empty]", transit.toString());
    }

    @Test
    public void toStringTest2() {
        Transit transit = new Transit(new LineNumber(1));
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
