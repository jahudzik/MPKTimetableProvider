package pl.jahu.mpk.utils;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-05.
 */
public class LineNumbersResolverTest {

    private static List<Line> allLinesNumeric;
    private static List<Line> allLinesWithLiteral;

    @BeforeClass
    public static void setUp() {
        Line line1 = new Line(new LineNumber(2));
        Line line2 = new Line(new LineNumber(3));
        Line line3 = new Line(new LineNumber(6));
        Line line4 = new Line(new LineNumber(7));
        Line line5 = new Line(new LineNumber(10));
        allLinesNumeric = Arrays.asList(line1, line2, line3, line4, line5); // 2, 3, 6, 7, 10

        Line line6 = new Line(new LineNumber("6a"));
        Line line7 = new Line(new LineNumber("6b"));
        Line line8 = new Line(new LineNumber("X"));
        allLinesWithLiteral = Arrays.asList(line1, line2, line3, line6, line7, line4, line5, line8); // 2, 3, 6, 6a, 6b, 7, 10, X
    }

    /******************** TESTS ********************/

    @Test
    public void constructor_test1() {
        LineNumbersResolver lineNumbersResolver = new LineNumbersResolver(true);
        TestUtils.checkCollectionSize(lineNumbersResolver.getLineNumbersCandidates(), 0);
    }

    @Test
    public void constructor_test2() {
        LineNumbersResolver lineNumbersResolver = new LineNumbersResolver(false);
        assertTrue(lineNumbersResolver.getLineNumbersCandidates().size() > 0);
    }

    @Test
    public void checkRange_test1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(1), new LineNumber(3));
        checkRange(lines, new int[]{0, 1});
    }

    @Test
    public void checkRange_test2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(2), new LineNumber(8));
        checkRange(lines, new int[]{0, 3});
    }

    @Test
    public void checkRange_test3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(5), new LineNumber(9));
        checkRange(lines, new int[]{2, 3});
    }

    @Test
    public void checkRange_test4() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(3), new LineNumber(3));
        checkRange(lines, new int[]{1, 1});
    }

    @Test
    public void checkRange_noResult1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(11), new LineNumber(20));
        checkRange(lines, new int[]{LineNumbersResolver.NO_LINE_NUMBER, LineNumbersResolver.NO_LINE_NUMBER});
    }

    @Test
    public void checkRange_noResult2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(7), new LineNumber(3));
        checkRange(lines, new int[]{LineNumbersResolver.NO_LINE_NUMBER, LineNumbersResolver.NO_LINE_NUMBER});
    }

    @Test
    public void checkRange_noResult3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesNumeric, new LineNumber(0), new LineNumber(1));
        checkRange(lines, new int[]{LineNumbersResolver.NO_LINE_NUMBER, LineNumbersResolver.NO_LINE_NUMBER});
    }

    // allLinesWithLiteral = [2, 3, 6, 6a, 6b, 7, 10, X]
    @Test
    public void checkRange_literal1() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber(6));
        checkRange(lines, new int[]{0, 2});
    }


    @Test
    public void checkRange_literal2() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber("6a"));
        checkRange(lines, new int[]{0, 3});
    }


    @Test
    public void checkRange_literal3() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber("6b"));
        checkRange(lines, new int[]{0, 4});
    }

    @Test
    public void checkRange_literal4() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(1), new LineNumber(7));
        checkRange(lines, new int[]{0, 5});
    }

    @Test
    public void checkRange_literal5() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber(100));
        checkRange(lines, new int[]{0, 6});
    }

    @Test
    public void checkRange_literal6() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber("C"));
        checkRange(lines, new int[]{0, 6});
    }

    @Test
    public void checkRange_literal7() {
        int[] lines = LineNumbersResolver.getLinesFromRange(allLinesWithLiteral, new LineNumber(0), new LineNumber("Z"));
        checkRange(lines, new int[]{0, 7});
    }

    @Test
    public void checkRange_literal8() {
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
