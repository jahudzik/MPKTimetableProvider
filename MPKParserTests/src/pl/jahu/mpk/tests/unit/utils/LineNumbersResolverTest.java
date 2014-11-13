package pl.jahu.mpk.tests.unit.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.tests.TestUtils;
import pl.jahu.mpk.utils.LineNumbersResolver;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumbersResolverTest {

    private static List<LineNumber> allLinesNumeric;
    private static List<LineNumber> allLinesWithLiteral;

    @BeforeClass
    public static void setUp() {
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

    /******************** TESTS ********************/

    @Test
    public void constructorTest1() {
        LineNumbersResolver lineNumbersResolver = new LineNumbersResolver(true);
        TestUtils.checkCollectionSize(lineNumbersResolver.getLineNumbersCandidates(), 0);
    }

    @Test
    public void constructorTest2() {
        LineNumbersResolver lineNumbersResolver = new LineNumbersResolver(false);
        assertTrue(lineNumbersResolver.getLineNumbersCandidates().size() > 0);
    }

    @Test
    public void testLinesFromRange1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(1), new LineNumber(3));
        checkRange(lines, new int[]{0, 1});
    }

    @Test
    public void testLinesFromRange2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(2), new LineNumber(8));
        checkRange(lines, new int[]{0, 3});
    }

    @Test
    public void testLinesFromRange3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(5), new LineNumber(9));
        checkRange(lines, new int[]{2, 3});
    }

    @Test
    public void testLinesFromRange4() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(3), new LineNumber(3));
        checkRange(lines, new int[]{1, 1});
    }

    @Test
    public void testLinesFromRangeNoResult1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(11), new LineNumber(20));
        checkRange(lines, new int[]{LineNumbersResolver.NO_LINE_NUMBER, LineNumbersResolver.NO_LINE_NUMBER});
    }

    @Test
    public void testLinesFromRangeNoResult2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(7), new LineNumber(3));
        checkRange(lines, new int[]{LineNumbersResolver.NO_LINE_NUMBER, LineNumbersResolver.NO_LINE_NUMBER});
    }

    @Test
    public void testLinesFromRangeNoResult3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(0), new LineNumber(1));
        checkRange(lines, new int[]{LineNumbersResolver.NO_LINE_NUMBER, LineNumbersResolver.NO_LINE_NUMBER});
    }

    // allLinesWithLiteral = [2, 3, 6, 6a, 6b, 7, 10, X]
    @Test
    public void testLiteralLinesInRange1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber(6));
        checkRange(lines, new int[]{0, 2});
    }


    @Test
    public void testLiteralLinesInRange2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber("6a"));
        checkRange(lines, new int[]{0, 3});
    }


    @Test
    public void testLiteralLinesInRange3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber("6b"));
        checkRange(lines, new int[]{0, 4});
    }

    @Test
    public void testLiteralLinesInRange4() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber(7));
        checkRange(lines, new int[]{0, 5});
    }

    @Test
    public void testLiteralLinesInRange5() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber(100));
        checkRange(lines, new int[]{0, 6});
    }

    @Test
    public void testLiteralLinesInRange6() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber("C"));
        checkRange(lines, new int[]{0, 6});
    }

    @Test
    public void testLiteralLinesInRange7() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber("Z"));
        checkRange(lines, new int[]{0, 7});
    }

    @Test
    public void testLiteralLinesInRange8() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber("A"), new LineNumber("Z"));
        checkRange(lines, new int[]{7, 7});
    }

    /******************** API ********************/

    public void checkRange(int[] range, int[] expectedRange) {
        assertEquals(expectedRange.length, range.length);
        for (int i = 0; i < expectedRange.length; i++) {
            assertEquals(expectedRange[i], range[i]);
        }
    }

}
