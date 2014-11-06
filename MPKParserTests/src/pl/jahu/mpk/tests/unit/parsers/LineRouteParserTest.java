package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tests.TestUtils;

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

    @Test(expected = ParsableDataNotFoundException.class)
    public void notParsableDataFoundTest() throws TimetableParseException, ParsableDataNotFoundException {
        timetableProvider.getLineRoute(new LineNumber("A"), 2);
    }

    @Test
    public void getLineRouteTest1() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(1);
        List<StationData> stations = timetableProvider.getLineRoute(lineNumber, 1);
        TestUtils.checkCollectionSize(stations, 29);
        checkStationData(stations.get(0), "Wzgórza Krzesławickie", lineNumber, 1);
        checkStationData(stations.get(28), "Salwator", lineNumber, 29);
    }

    @Test
    public void getLineRouteTest2() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(22);
        List<StationData> stations = timetableProvider.getLineRoute(lineNumber, 1);
        TestUtils.checkCollectionSize(stations, 37);
        checkStationData(stations.get(0), "Borek Fałęcki", lineNumber, 1);
        checkStationData(stations.get(36), "Agencja Kraków Wschód NŻ", lineNumber, 37);
    }

    @Test
    public void getLineRouteTest3() throws ParsableDataNotFoundException, TimetableParseException {
        LineNumber lineNumber = new LineNumber(248);
        List<StationData> stations = timetableProvider.getLineRoute(lineNumber, 1);
        TestUtils.checkCollectionSize(stations, 22);
        checkStationData(stations.get(0), "Bronowice Małe", lineNumber, 1);
        checkStationData(stations.get(21), "Zelków I NŻ", lineNumber, 22);
    }

    @Test
    public void retrieveDestinationTest1() throws ParsableDataNotFoundException, TimetableParseException {
        assertEquals("Salwator", timetableProvider.getLineRouteDestination(new LineNumber(1), 1));
    }

    @Test
    public void retrieveDestinationTest2() throws ParsableDataNotFoundException, TimetableParseException {
        assertEquals("Walcownia", timetableProvider.getLineRouteDestination(new LineNumber(22), 1));
    }

    @Test
    public void retrieveDestinationTest3() throws ParsableDataNotFoundException, TimetableParseException {
        assertEquals("Zelków", timetableProvider.getLineRouteDestination(new LineNumber(248), 1));
    }


    /******************** API ********************/

    private void checkStationData(StationData stationData, String expectedName, LineNumber expectedLineNumber, int expectedSequenceNumber) {
        assertNotNull(stationData);
        assertEquals(expectedName, stationData.getName());
        assertEquals(expectedLineNumber, stationData.getLineNumber());
        assertEquals(expectedSequenceNumber, stationData.getSequenceNumber());
    }

}
