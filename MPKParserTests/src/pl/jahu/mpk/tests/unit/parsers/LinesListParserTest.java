package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.DefaultTestModule;
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

    private List<LineNumber> lines;

    @Before
    public void setUp() throws UnsupportedLineNumberException, TimetableNotFoundException, NoDataProvidedException {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);

        lines = timetableProvider.getLinesList();
        assertNotNull(lines);
        assertEquals(169, lines.size());
    }

    /******************** TESTS ********************/

    @Test
    public void testExpectedNumber1() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(0));
    }

    @Test
    public void testExpectedNumber2() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(1));
    }

    @Test
    public void testExpectedNumber3() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(72));
    }

    @Test
    public void testExpectedNumber4() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(301));
    }

    @Test
    public void testExpectedNumber5() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(352));
    }

    @Test
    public void testExpectedNumber6() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(238));
    }

    @Test
    public void testExpectedNumber7() throws UnsupportedLineNumberException {
        checkIfContains(new LineNumber(915));
    }


    @Test
    public void testUnxpectedNumber1() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber(303));
    }

    @Test
    public void testUnxpectedNumber2() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber(5));
    }

    @Test
    public void testUnxpectedNumber3() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber(298));
    }

    @Test
    public void testUnxpectedNumber4() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber(1000));
    }

    @Test
    public void testUnxpectedNumber5() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber("A"));
    }

    @Test
    public void testUnxpectedNumber6() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber("69a"));
    }

    @Test
    public void testUnxpectedNumber7() throws UnsupportedLineNumberException {
        checkIfDoesntContain(new LineNumber("Z8"));
    }


    /******************* API ********************/

    private void checkIfContains(LineNumber lineNumber) {
        assertTrue(lines.contains(lineNumber));
    }

    private void checkIfDoesntContain(LineNumber lineNumber) {
        assertFalse(lines.contains(lineNumber));
    }

}
