package pl.jahu.mpk.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.TestUtils;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;

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

    private LineRouteParser lineRouteParser;

    @Before
    public void setUp() {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);
        lineRouteParser = new LineRouteParser();
    }

    /******************** TESTS ********************/

    @Test
    public void getLineRoute_test1() throws ParsableDataNotFoundException, TimetableParseException {
        Line line = new Line(1, TestUtils.EXAMPLE_LINE_TYPE);
        ParsableData parsableData = timetableProvider.getLineRouteDocument(line.getNumber(), 1);

        List<StationData> stations =  lineRouteParser.parse(line, parsableData);

        TestUtils.checkCollectionSize(stations, 29);
        checkStationData(stations.get(0), "Wzgórza Krzesławickie", line, 1);
        checkStationData(stations.get(28), "Salwator", line, 29);
    }

    @Test
    public void getLineRoute_test2() throws ParsableDataNotFoundException, TimetableParseException {
        Line line = new Line(22, TestUtils.EXAMPLE_LINE_TYPE);
        ParsableData parsableData = timetableProvider.getLineRouteDocument(line.getNumber(), 1);

        List<StationData> stations =  lineRouteParser.parse(line, parsableData);

        TestUtils.checkCollectionSize(stations, 37);
        checkStationData(stations.get(0), "Borek Fałęcki", line, 1);
        checkStationData(stations.get(36), "Agencja Kraków Wschód NŻ", line, 37);
    }

    @Test
    public void getLineRoute_test3() throws ParsableDataNotFoundException, TimetableParseException {
        Line line = new Line(248, TestUtils.EXAMPLE_LINE_TYPE);
        ParsableData parsableData = timetableProvider.getLineRouteDocument(line.getNumber(), 1);

        List<StationData> stations =  lineRouteParser.parse(line, parsableData);

        TestUtils.checkCollectionSize(stations, 22);
        checkStationData(stations.get(0), "Bronowice Małe", line, 1);
        checkStationData(stations.get(21), "Zelków I NŻ", line, 22);
    }

    @Test
    public void getLineRouteDestination_test1() throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData parsableData = timetableProvider.getLineRouteDocument(new LineNumber(1), 1);
        assertEquals("Salwator", lineRouteParser.retrieveDestination(parsableData));
    }

    @Test
    public void getLineRouteDestination_test2() throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData parsableData = timetableProvider.getLineRouteDocument(new LineNumber(22), 1);
        assertEquals("Walcownia", lineRouteParser.retrieveDestination(parsableData));
    }

    @Test
    public void getLineRouteDestination_test3() throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData parsableData = timetableProvider.getLineRouteDocument(new LineNumber(248), 1);
        assertEquals("Zelków", lineRouteParser.retrieveDestination(parsableData));
    }


    /******************** API ********************/

    private void checkStationData(StationData stationData, String expectedName, Line expectedLine, int expectedSequenceNumber) {
        assertNotNull(stationData);
        assertEquals(expectedName, stationData.getName());
        assertEquals(expectedLine, stationData.getLine());
        assertEquals(expectedSequenceNumber, stationData.getSequenceNumber());
    }

}
