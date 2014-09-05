package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.parser.utils.LineNumbersResolver;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumbersResolverTest {

    private static final List<Integer> ALL_LINES = Arrays.asList(2, 3, 6, 7, 10);

    @Test
    public void testLinesFromRange1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 1, 3);
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(1, lines[1]);
    }

    @Test
    public void testLinesFromRange2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 2, 8);
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(3, lines[1]);
    }

    @Test
    public void testLinesFromRange3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 5, 9);
        assertEquals(2, lines.length);
        assertEquals(2, lines[0]);
        assertEquals(3, lines[1]);
    }

    @Test
    public void testLinesFromRange4() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 3, 3);
        assertEquals(2, lines.length);
        assertEquals(1, lines[0]);
        assertEquals(1, lines[1]);
    }

    @Test
    public void testLinesFromRangeNoResult1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 11, 20);
        assertEquals(2, lines.length);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[0]);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[1]);
    }

    @Test
    public void testLinesFromRangeNoResult2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 7, 3);
        assertEquals(2, lines.length);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[0]);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[1]);
    }

    @Test
    public void testLinesFromRangeNoResult3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(ALL_LINES, 0, 1);
        assertEquals(2, lines.length);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[0]);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[1]);
    }
}
