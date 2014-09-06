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

    @Test
    public void testInitialization() throws UnsupportedLineNumberException {
        Transit transit = new Transit(new LineNumber(123));
        assertNotNull(transit.getStops());
        assertEquals(transit.getStops().size(), 0);
    }


    @Test
    public void testAddingStops() throws UnsupportedLineNumberException {
        TransitStop stop1 = new TransitStop(new Time(15, 20), "station1");
        TransitStop stop2 = new TransitStop(new Time(15, 30), "station2");
        TransitStop stop3 = new TransitStop(new Time(15, 25), "station3");

        Transit transit = new Transit(new LineNumber(123));

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
