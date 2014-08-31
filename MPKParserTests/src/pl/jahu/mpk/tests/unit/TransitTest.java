package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.entities.Transit;
import pl.jahu.mpk.entities.TransitStop;
import pl.jahu.mpk.parser.utils.Time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class TransitTest {

    @Test
    public void testInitialization() {
        Transit transit = new Transit(123);
        assertNotNull(transit.getStops());
        assertEquals(transit.getStops().size(), 0);
    }


    @Test
    public void testAddingStops() {
        TransitStop stop1 = new TransitStop(new Time(15, 20), "station1");
        TransitStop stop2 = new TransitStop(new Time(15, 30), "station2");
        TransitStop stop3 = new TransitStop(new Time(15, 25), "station3");

        Transit transit = new Transit(123);

        transit.addStop(stop1);
        assertEquals(transit.getStops().size(), 1);
        assertEquals(transit.getStops().get(0), stop1);

        transit.addStop(stop2);
        assertEquals(transit.getStops().size(), 2);
        assertEquals(transit.getStops().get(0), stop1);
        assertEquals(transit.getStops().get(1), stop2);

        transit.addStop(stop3);
        assertEquals(transit.getStops().size(), 3);
        assertEquals(transit.getStops().get(0), stop1);
        assertEquals(transit.getStops().get(1), stop3);
        assertEquals(transit.getStops().get(2), stop2);
    }

}
