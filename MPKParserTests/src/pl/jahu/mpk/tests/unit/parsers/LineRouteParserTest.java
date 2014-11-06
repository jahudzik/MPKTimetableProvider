package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.StationData;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tests.TestUtils;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LineRouteParserTest {

    @Inject
    TimetableProvider timetableProvider;

    @Before
    public void setUp() {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);
    }

    /******************** TESTS ********************/

    @Test
    public void getLineRouteTest1() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        List<StationData> stations = timetableProvider.getLineRoute(new LineNumber(1), 1);
        TestUtils.checkCollectionSize(stations, 29);
        checkStationData(stations.get(0), "Wzgórza Krzesławickie", "0001t001.htm");
        checkStationData(stations.get(28), "Salwator", "0001t029.htm");
    }

    @Test
    public void getLineRouteTest2() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        List<StationData> stations = timetableProvider.getLineRoute(new LineNumber(22), 1);
        TestUtils.checkCollectionSize(stations, 37);
        checkStationData(stations.get(0), "Borek Fałęcki", "0022t001.htm");
        checkStationData(stations.get(36), "Agencja Kraków Wschód NŻ", "0022t037.htm");
    }

    @Test
    public void getLineRouteTest3() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        List<StationData> stations = timetableProvider.getLineRoute(new LineNumber(248), 1);
        TestUtils.checkCollectionSize(stations, 22);
        checkStationData(stations.get(0), "Bronowice Małe", "0248t001.htm");
        checkStationData(stations.get(21), "Zelków I NŻ", "0248t022.htm");
    }

    @Test
    public void retrieveDestinationTest1() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        assertEquals("Salwator", timetableProvider.getLineRouteDestination(new LineNumber(1), 1));
    }

    @Test
    public void retrieveDestinationTest2() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        assertEquals("Walcownia", timetableProvider.getLineRouteDestination(new LineNumber(22), 1));
    }

    @Test
    public void retrieveDestinationTest3() throws UnsupportedLineNumberException, TimetableNotFoundException, LineRouteParseException {
        assertEquals("Zelków", timetableProvider.getLineRouteDestination(new LineNumber(248), 1));
    }


    /******************** API ********************/

    private void checkStationData(StationData stationData, String expectedName, String expectedUrlLocation) {
        assertNotNull(stationData);
        assertEquals(expectedName, stationData.getName());
        assertEquals(expectedUrlLocation, stationData.getUrlLocation());
    }

}
