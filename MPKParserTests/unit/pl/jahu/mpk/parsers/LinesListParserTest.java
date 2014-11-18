package pl.jahu.mpk.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.TestUtils;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineType;
import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.providers.TimetableProvider;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParserTest {

    @Inject
    TimetableProvider timetableProvider;

    private LinesListParser linesListParser;

    @Before
    public void setUp() {
        linesListParser = new LinesListParser();
    }

    /******************** TESTS ********************/

    @Test
    public void getLinesList_numbericOnly() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        TestUtils.checkCollectionSize(lines, 169);
    }

    @Test
    public void getLinesList_literals() throws ParsableDataNotFoundException {
        initModule("2014-10-17");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        TestUtils.checkCollectionSize(lines, 184);
    }


    @Test
    public void getChangedLinesList_test1() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseChanged(timetableProvider.getLinesListDocument());
        TestUtils.checkCollectionSize(lines, 11);
    }

    @Test
    public void getChangedLinesList_test2() throws ParsableDataNotFoundException {
        initModule("2014-11-01");
        List<Line> lines = linesListParser.parseChanged(timetableProvider.getLinesListDocument());
        TestUtils.checkCollectionSize(lines, 47);
    }

    @Test
    public void getChangedLinesList_oneChangeOnly() throws ParsableDataNotFoundException {
        initModule("2014-10-17");
        List<Line> lines = linesListParser.parseChanged(timetableProvider.getLinesListDocument());
        TestUtils.checkCollectionSize(lines, 1);
    }

    @Test
    public void getChangedLinesList_noChanges() throws ParsableDataNotFoundException {
        initModule("no_changes");
        List<Line> lines = linesListParser.parseChanged(timetableProvider.getLinesListDocument());
        TestUtils.checkCollectionSize(lines, 0);
    }


    @Test
    public void getLinesList_tramStandardCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 1, VehicleTypes.TRAM, ReasonTypes.STANDARD, AreaTypes.CITY);
        checkExpectedLine(lines, 52, VehicleTypes.TRAM, ReasonTypes.STANDARD, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_tramReplacementCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 72, VehicleTypes.TRAM, ReasonTypes.REPLACEMENT, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_tramSpecialCity1() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 0, VehicleTypes.TRAM, ReasonTypes.SPECIAL, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_tramSpecialCity2() throws ParsableDataNotFoundException {
        initModule("2014-11-01");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 86, VehicleTypes.TRAM, ReasonTypes.SPECIAL, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_tramNightlyCity1() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 62, VehicleTypes.TRAM, ReasonTypes.NIGHTLY, AreaTypes.CITY);
        checkExpectedLine(lines, 69, VehicleTypes.TRAM, ReasonTypes.NIGHTLY, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_tramNightlyCity2() throws ParsableDataNotFoundException {
        initModule("2014-11-01");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, "64a", VehicleTypes.TRAM, ReasonTypes.NIGHTLY, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_busStandardCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 100, VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        checkExpectedLine(lines, 159, VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        checkExpectedLine(lines, 179, VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_busStandardAgglomeration() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 201, VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.AGGLOMERATION);
        checkExpectedLine(lines, 202, VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.AGGLOMERATION);
    }

    @Test
    public void getLinesList_busRapidAgglomeration() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 301, VehicleTypes.BUS, ReasonTypes.RAPID, AreaTypes.AGGLOMERATION);
        checkExpectedLine(lines, 352, VehicleTypes.BUS, ReasonTypes.RAPID, AreaTypes.AGGLOMERATION);
    }

    @Test
    public void getLinesList_busAdditionalCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 405, VehicleTypes.BUS, ReasonTypes.ADDITIONAL, AreaTypes.CITY);
        checkExpectedLine(lines, 422, VehicleTypes.BUS, ReasonTypes.ADDITIONAL, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_busRapidCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 502, VehicleTypes.BUS, ReasonTypes.RAPID, AreaTypes.CITY);
        checkExpectedLine(lines, 572, VehicleTypes.BUS, ReasonTypes.RAPID, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_busNightlyCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 601, VehicleTypes.BUS, ReasonTypes.NIGHTLY, AreaTypes.CITY);
        checkExpectedLine(lines, 605, VehicleTypes.BUS, ReasonTypes.NIGHTLY, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_busReplacementCity() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 704, VehicleTypes.BUS, ReasonTypes.REPLACEMENT, AreaTypes.CITY);
        checkExpectedLine(lines, 724, VehicleTypes.BUS, ReasonTypes.REPLACEMENT, AreaTypes.CITY);
    }

    @Test
    public void getLinesList_busNightlyAgglomeration() throws ParsableDataNotFoundException {
        initModule("default");
        List<Line> lines = linesListParser.parseAll(timetableProvider.getLinesListDocument());
        checkExpectedLine(lines, 902, VehicleTypes.BUS, ReasonTypes.NIGHTLY, AreaTypes.AGGLOMERATION);
        checkExpectedLine(lines, 904, VehicleTypes.BUS, ReasonTypes.NIGHTLY, AreaTypes.AGGLOMERATION);
    }


    /******************** API ********************/

    private void initModule(String filesLocation) {
        DaggerApplication.init(new DefaultTestModule(filesLocation));
        DaggerApplication.inject(this);
    }

    private void checkExpectedLine(List<Line> lines, int expectedNumber, VehicleTypes expectedVehicleType, ReasonTypes expectedReasonType, AreaTypes expectedAreaType) {
        assertTrue("Didn't find expected line number [" + expectedNumber + "]", lines.contains(new Line(expectedNumber, new LineType(expectedVehicleType, expectedReasonType, expectedAreaType))));
    }

    private void checkExpectedLine(List<Line> lines, String expectedNumber, VehicleTypes expectedVehicleType, ReasonTypes expectedReasonType, AreaTypes expectedAreaType) {
        assertTrue("Didn't find expected line number [" + expectedNumber + "]", lines.contains(new Line(expectedNumber, new LineType(expectedVehicleType, expectedReasonType, expectedAreaType))));
    }

}
