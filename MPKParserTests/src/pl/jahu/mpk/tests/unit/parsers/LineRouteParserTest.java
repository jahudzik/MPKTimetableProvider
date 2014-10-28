package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerTestApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.StationData;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LineRouteParserTest {

    @Inject
    TimetableProvider timetableProvider;

    @Before
    public void setUp() {
        DaggerTestApplication.init();
        DaggerTestApplication.inject(this);
    }

    @Test
    public void retrieveDestinationTest1() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        assertEquals("Salwator", timetableProvider.getLineRouteDestination(new LineNumber(1), 1));
    }

    @Test
    public void getLineRouteTest1() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        List<StationData> stations = timetableProvider.getLineRoute(new LineNumber(1), 1);
        assertNotNull(stations);
        assertEquals(29, stations.size());

        StationData station1 = stations.get(0);
        assertNotNull(station1);
        assertEquals("Wzgórza Krzesławickie", station1.getName());
        assertEquals("0001t001.htm", station1.getUrlLocation());

        StationData station2 = stations.get(28);
        assertNotNull(station2);
        assertEquals("Salwator", station2.getName());
        assertEquals("0001t029.htm", station2.getUrlLocation());
    }

    @Test
    public void retrieveDestinationTest2() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        assertEquals("Walcownia", timetableProvider.getLineRouteDestination(new LineNumber(22), 1));
    }

    @Test
    public void getLineRouteTest2() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        List<StationData> stations = timetableProvider.getLineRoute(new LineNumber(22), 1);
        assertNotNull(stations);
        assertEquals(37, stations.size());

        StationData station1 = stations.get(0);
        assertNotNull(station1);
        assertEquals("Borek Fałęcki", station1.getName());
        assertEquals("0022t001.htm", station1.getUrlLocation());

        StationData station2 = stations.get(36);
        assertNotNull(station2);
        assertEquals("Agencja Kraków Wschód NŻ", station2.getName());
        assertEquals("0022t037.htm", station2.getUrlLocation());
    }

    @Test
    public void retrieveDestinationTest3() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        assertEquals("Zelków", timetableProvider.getLineRouteDestination(new LineNumber(248), 1));
    }

    @Test
    public void getLineRouteTest3() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        List<StationData> stations = timetableProvider.getLineRoute(new LineNumber(248), 1);
        assertNotNull(stations);
        assertEquals(22, stations.size());

        StationData station1 = stations.get(0);
        assertNotNull(station1);
        assertEquals("Bronowice Małe", station1.getName());
        assertEquals("0248t001.htm", station1.getUrlLocation());

        StationData station2 = stations.get(21);
        assertNotNull(station2);
        assertEquals("Zelków I NŻ", station2.getName());
        assertEquals("0248t022.htm", station2.getUrlLocation());
    }

}
