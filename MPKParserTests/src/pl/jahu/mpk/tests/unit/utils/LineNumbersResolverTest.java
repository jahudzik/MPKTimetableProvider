package pl.jahu.mpk.tests.unit.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.utils.LineNumbersResolver;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumbersResolverTest {

    private static List<LineNumber> allLinesNumeric;
    private static List<LineNumber> allLinesWithLiteral;

    @BeforeClass
    public static void setUp() throws UnsupportedLineNumberException {
        LineNumber line1 = new LineNumber(2);
        LineNumber line2 = new LineNumber(3);
        LineNumber line3 = new LineNumber(6);
        LineNumber line4 = new LineNumber(7);
        LineNumber line5 = new LineNumber(10);
        allLinesNumeric = Arrays.asList(line1, line2, line3, line4, line5); // 2, 3, 6, 7, 10

        LineNumber line6 = new LineNumber("6a");
        LineNumber line7 = new LineNumber("6b");
        LineNumber line8 = new LineNumber("X");
        allLinesWithLiteral = Arrays.asList(line1, line2, line3, line6, line7, line4, line5, line8); // 2, 3, 6, 6a, 6b, 7, 10, X
    }

    @Test
    public void testLinesFromRange1() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(1), new LineNumber(3));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(1, lines[1]);
    }

    @Test
    public void testLinesFromRange2() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(2), new LineNumber(8));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(3, lines[1]);
    }

    @Test
    public void testLinesFromRange3() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(5), new LineNumber(9));
        assertEquals(2, lines.length);
        assertEquals(2, lines[0]);
        assertEquals(3, lines[1]);
    }

    @Test
    public void testLinesFromRange4() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(3), new LineNumber(3));
        assertEquals(2, lines.length);
        assertEquals(1, lines[0]);
        assertEquals(1, lines[1]);
    }

    @Test
    public void testLinesFromRangeNoResult1() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(11), new LineNumber(20));
        assertEquals(2, lines.length);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[0]);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[1]);
    }

    @Test
    public void testLinesFromRangeNoResult2() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(7), new LineNumber(3));
        assertEquals(2, lines.length);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[0]);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[1]);
    }

    @Test
    public void testLinesFromRangeNoResult3() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(0), new LineNumber(1));
        assertEquals(2, lines.length);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[0]);
        assertEquals(LineNumbersResolver.NO_LINE_NUMBER, lines[1]);
    }

    // allLinesWithLiteral = [2, 3, 6, 6a, 6b, 7, 10, X]
    @Test
    public void testLiteralLinesInRange1() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber(6));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(2, lines[1]);
    }


    @Test
    public void testLiteralLinesInRange2() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber("6a"));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(3, lines[1]);
    }


    @Test
    public void testLiteralLinesInRange3() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber("6b"));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(4, lines[1]);
    }

    @Test
    public void testLiteralLinesInRange4() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber(7));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(5, lines[1]);
    }

    @Test
    public void testLiteralLinesInRange5() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber(100));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(6, lines[1]);
    }

    @Test
    public void testLiteralLinesInRange6() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber("C"));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(6, lines[1]);
    }

    @Test
    public void testLiteralLinesInRange7() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber("Z"));
        assertEquals(2, lines.length);
        assertEquals(0, lines[0]);
        assertEquals(7, lines[1]);
    }

    @Test
    public void testLiteralLinesInRange8() throws UnsupportedLineNumberException {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber("A"), new LineNumber("Z"));
        assertEquals(2, lines.length);
        assertEquals(7, lines[0]);
        assertEquals(7, lines[1]);
    }

}