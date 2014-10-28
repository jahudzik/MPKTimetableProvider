package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.jahu.mpk.DaggerTestApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

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
    public void setUp() {
        DaggerTestApplication.init();
        DaggerTestApplication.inject(this);
    }

    @Test
    public void testAllLines() throws UnsupportedLineNumberException, TimetableNotFoundException, NoDataProvidedException {
        List<LineNumber> lines = timetableProvider.getLinesList();
        assertNotNull(lines);
        assertEquals(169, lines.size());
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


}
