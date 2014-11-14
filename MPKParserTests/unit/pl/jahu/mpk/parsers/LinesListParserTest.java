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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParserTest {

    @Inject
    TimetableProvider timetableProvider;

    private List<LineNumber> lines;

    @Before
    public void setUp() throws ParsableDataNotFoundException {
        DaggerApplication.init(new DefaultTestModule());
        DaggerApplication.inject(this);

        lines = timetableProvider.getLinesList();
        TestUtils.checkCollectionSize(lines, 169);
    }

    /******************** TESTS ********************/

    @Test
    public void testExpectedNumber1() {
        checkIfContains(new LineNumber(0));
    }

    @Test
    public void testExpectedNumber2() {
        checkIfContains(new LineNumber(1));
    }

    @Test
    public void testExpectedNumber3() {
        checkIfContains(new LineNumber(72));
    }

    @Test
    public void testExpectedNumber4() {
        checkIfContains(new LineNumber(301));
    }

    @Test
    public void testExpectedNumber5() {
        checkIfContains(new LineNumber(352));
    }

    @Test
    public void testExpectedNumber6() {
        checkIfContains(new LineNumber(238));
    }

    @Test
    public void testExpectedNumber7() {
        checkIfContains(new LineNumber(915));
    }


    @Test
    public void testUnxpectedNumber1() {
        checkIfDoesntContain(new LineNumber(303));
    }

    @Test
    public void testUnxpectedNumber2() {
        checkIfDoesntContain(new LineNumber(5));
    }

    @Test
    public void testUnxpectedNumber3() {
        checkIfDoesntContain(new LineNumber(298));
    }

    @Test
    public void testUnxpectedNumber4() {
        checkIfDoesntContain(new LineNumber(999));
    }

    @Test
    public void testUnxpectedNumber5() {
        checkIfDoesntContain(new LineNumber("A"));
    }

    @Test
    public void testUnxpectedNumber6() {
        checkIfDoesntContain(new LineNumber("69a"));
    }

    @Test
    public void testUnxpectedNumber7() {
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
