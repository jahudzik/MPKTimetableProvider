package pl.jahu.mpk.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.TestUtils;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParserTest {

    @Inject
    TimetableProvider timetableProvider;

    /******************** TESTS ********************/

    @Test
    public void getLinesList_numbericOnly() throws ParsableDataNotFoundException {
        initModule("default");

        List<LineNumber> lines = timetableProvider.getLinesList();

        TestUtils.checkCollectionSize(lines, 169);
        checkExpectedLines(lines, 0, 1, 72, 301, 352, 238, 915);
        checkUnexpectedLines(lines, 5, 298, 303, 1000);
    }

    @Test
    public void getLinesList_literals() throws ParsableDataNotFoundException {
        initModule("2014-10-17");

        List<LineNumber> lines = timetableProvider.getLinesList();

        TestUtils.checkCollectionSize(lines, 184);
        checkExpectedLines(lines, 62, 79, 100);
        checkExpectedLines(lines, "62a", "64a", "69a");
    }

    @Test
    public void getChangedLinesList_test1() throws ParsableDataNotFoundException {
        initModule("default");

        List<LineNumber> lines = timetableProvider.getChangedLinesList();

        TestUtils.checkCollectionSize(lines, 11);
        checkExpectedLines(lines, 62, 102, 109, 120, 138, 142, 159, 193, 301, 304, 502);
        checkUnexpectedLines(lines, 1, 72, 601, 602, 1000);
    }

    @Test
    public void getChangedLinesList_test2() throws ParsableDataNotFoundException {
        initModule("2014-11-01");

        List<LineNumber> lines = timetableProvider.getChangedLinesList();

        TestUtils.checkCollectionSize(lines, 47);
        checkExpectedLines(lines, 1, 2, 81, 87, 100, 139, 424, 503, 801, 802, 884);
    }

    @Test
    public void getChangedLinesList_oneChangeOnly() throws ParsableDataNotFoundException {
        initModule("2014-10-17");

        List<LineNumber> lines = timetableProvider.getChangedLinesList();

        TestUtils.checkCollectionSize(lines, 1);
        checkExpectedLines(lines, "64a");
    }

    @Test
    public void getChangedLinesList_noChanges() throws ParsableDataNotFoundException {
        initModule("no_changes");

        List<LineNumber> lines = timetableProvider.getChangedLinesList();

        TestUtils.checkCollectionSize(lines, 0);
    }


    /******************** API ********************/

    private void initModule(String filesLocation) {
        DaggerApplication.init(new DefaultTestModule(filesLocation));
        DaggerApplication.inject(this);
    }

    private void checkExpectedLines(List<LineNumber> lines, int... expectedNumbers) {
        for (int expectedNumber : expectedNumbers) {
            assertTrue("Didn't find expected line number [" + expectedNumber + "]", lines.contains(new LineNumber(expectedNumber)));
        }
    }

    private void checkExpectedLines(List<LineNumber> lines, String... expectedNumbers) {
        for (String expectedNumber : expectedNumbers) {
            assertTrue("Didn't find expected line number [" + expectedNumber + "]",lines.contains(new LineNumber(expectedNumber)));
        }
    }

    private void checkUnexpectedLines(List<LineNumber> lines, int... expectedNumbers) {
        for (int expectedNumber : expectedNumbers) {
            assertFalse("Found unexpected line number [" + expectedNumber + "]", lines.contains(new LineNumber(expectedNumber)));
        }
    }

}
