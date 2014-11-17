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

    @Before
    public void setUp() throws ParsableDataNotFoundException {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);
    }

    /******************** TESTS ********************/

    @Test
    public void getLinesList_test() throws ParsableDataNotFoundException {
        List<LineNumber> lines = timetableProvider.getLinesList();

        TestUtils.checkCollectionSize(lines, 169);

        assertTrue(lines.contains(new LineNumber(0)));
        assertTrue(lines.contains((new LineNumber(1))));
        assertTrue(lines.contains((new LineNumber(72))));
        assertTrue(lines.contains((new LineNumber(301))));
        assertTrue(lines.contains((new LineNumber(352))));
        assertTrue(lines.contains((new LineNumber(238))));
        assertTrue(lines.contains((new LineNumber(915))));

        assertFalse(lines.contains((new LineNumber(303))));
        assertFalse(lines.contains((new LineNumber(5))));
        assertFalse(lines.contains((new LineNumber(298))));
        assertFalse(lines.contains((new LineNumber(1000))));
    }

    @Test
    public void getChangedLinesList_test() throws ParsableDataNotFoundException {
        List<LineNumber> lines = timetableProvider.getChangedLinesList();

        TestUtils.checkCollectionSize(lines, 11);

        assertTrue(lines.contains(new LineNumber(62)));
        assertTrue(lines.contains((new LineNumber(102))));
        assertTrue(lines.contains((new LineNumber(109))));
        assertTrue(lines.contains((new LineNumber(120))));
        assertTrue(lines.contains((new LineNumber(138))));
        assertTrue(lines.contains((new LineNumber(142))));
        assertTrue(lines.contains((new LineNumber(159))));
        assertTrue(lines.contains((new LineNumber(193))));
        assertTrue(lines.contains((new LineNumber(301))));
        assertTrue(lines.contains((new LineNumber(304))));
        assertTrue(lines.contains((new LineNumber(502))));

        assertFalse(lines.contains((new LineNumber(1))));
        assertFalse(lines.contains((new LineNumber(72))));
        assertFalse(lines.contains((new LineNumber(601))));
        assertFalse(lines.contains((new LineNumber(602))));
        assertFalse(lines.contains((new LineNumber(1000))));
    }

}
