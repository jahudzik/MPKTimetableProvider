package pl.jahu.mpk.tests.unit.entities;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Transit;
import pl.jahu.mpk.entities.TransitStop;
import pl.jahu.mpk.utils.Time;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class TransitTest {

    /******************** TESTS ********************/

    @Test
    public void testInitialization() throws UnsupportedLineNumberException {
        Transit transit = new Transit(new LineNumber(123));
        checkStopsList(transit, new TransitStop[]{});
    }


    @Test
    public void testAddingStops() throws UnsupportedLineNumberException {
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


    /******************* API ********************/

    private void checkStopsList(Transit transit, TransitStop[] expectedStops) {
        assertEquals(expectedStops.length, transit.getStops().size());
        for (int i = 0; i < expectedStops.length; i++) {
            assertEquals(expectedStops[i], transit.getStops().get(i));
        }
    }

}
