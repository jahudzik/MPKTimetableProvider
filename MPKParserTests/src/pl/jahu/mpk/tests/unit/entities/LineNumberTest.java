package pl.jahu.mpk.tests.unit.entities;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-06.
 */
public class LineNumberTest {

    /******************** TESTS ********************/

    @Test(expected = UnsupportedLineNumberException.class)
    public void testLiteralConstructor1() throws UnsupportedLineNumberException {
        new LineNumber("");
    }

    @Test(expected = UnsupportedLineNumberException.class)
    public void testLiteralConstructor2() throws UnsupportedLineNumberException {
        new LineNumber(null);
    }

    @Test(expected = UnsupportedLineNumberException.class)
    public void testNumericConstructor1() throws UnsupportedLineNumberException {
        new LineNumber(-1);
    }

    @Test
    public void testNumericConstructor2() throws UnsupportedLineNumberException {
        checkNumber(new LineNumber(3), 3, "3", true);
    }

    @Test
    public void testLiteralConstructor3() throws UnsupportedLineNumberException {
        checkNumber(new LineNumber(16), 16, "16", true);
    }


    @Test
    public void testLiteralConstructor4() throws UnsupportedLineNumberException {
        checkNumber(new LineNumber("Z8"), 8, "Z8", false);
    }

    @Test
    public void testLiteralConstructor5() throws UnsupportedLineNumberException {
        checkNumber(new LineNumber("62A"), 62, "62A", false);
    }

    @Test
    public void testLiteralConstructor6() throws UnsupportedLineNumberException {
        checkNumber(new LineNumber("D"), -1, "D", false);
    }


    @Test
    public void testComparator1() throws UnsupportedLineNumberException {
        checkDifferentNumbersComparison(new LineNumber("3"), new LineNumber("7"));
    }

    @Test
    public void testComparator2() throws UnsupportedLineNumberException {
        checkDifferentNumbersComparison(new LineNumber("3A"), new LineNumber("7"));
    }

    @Test
    public void testComparator3() throws UnsupportedLineNumberException {
        checkDifferentNumbersComparison(new LineNumber("3"), new LineNumber("3B"));
    }

    @Test
    public void testComparator4() throws UnsupportedLineNumberException {
        checkDifferentNumbersComparison(new LineNumber("B"), new LineNumber("C2"));
    }


    @Test
    public void testComparator5() throws UnsupportedLineNumberException {
        checkDifferentNumbersComparison(new LineNumber("D"), new LineNumber("L"));
    }

    @Test
    public void testComparator6() throws UnsupportedLineNumberException {
        checkDifferentNumbersComparison(new LineNumber("5A"), new LineNumber("5B"));
    }

    @Test
    public void testEquals1() throws UnsupportedLineNumberException {
        checkEqualNumbersComparison(new LineNumber("5"), new LineNumber("5"), true);
    }

    @Test
    public void testEquals2() throws UnsupportedLineNumberException {
        checkEqualNumbersComparison(new LineNumber(5), new LineNumber("5"), true);
    }


    @Test
    public void testEquals3() throws UnsupportedLineNumberException {
        checkEqualNumbersComparison(new LineNumber("5"), new LineNumber("5A"), false);
    }


    @Test
    public void testEquals4() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("5");
        assertTrue(lineNumber1.equals(5));
    }


    @Test
    public void testEquals5() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber(5);
        assertTrue(lineNumber1.equals(5));
    }

    @Test
    public void testSorting1() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber(13));
        numbers.add(new LineNumber("6"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(6), new LineNumber(13)});
    }

    @Test
    public void testSorting2() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("6a"));
        numbers.add(new LineNumber("6"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(6), new LineNumber("6a")});
    }

    @Test
    public void testSorting3() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber(13));
        numbers.add(new LineNumber("Z6"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(13), new LineNumber("Z6")});
    }


    @Test
    public void testSorting4() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber(16));
        numbers.add(new LineNumber("X"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(16), new LineNumber("X")});
    }

    @Test
    public void testSorting5() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("8c"));
        numbers.add(new LineNumber("8e"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("8c"), new LineNumber("8e")});
    }

    @Test
    public void testSorting6() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("8c"));
        numbers.add(new LineNumber("E8"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("8c"), new LineNumber("E8")});
    }

    @Test
    public void testSorting7() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("A"));
        numbers.add(new LineNumber("8e"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("8e"), new LineNumber("A")});
    }

    @Test
    public void testSorting8() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("Z2"));
        numbers.add(new LineNumber("Z1"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("Z1"), new LineNumber("Z2")});
    }

    @Test
    public void testSorting9() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("M"));
        numbers.add(new LineNumber("M4"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("M"), new LineNumber("M4")});
    }

    @Test
    public void testSorting10() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("TM"));
        numbers.add(new LineNumber("P"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("P"), new LineNumber("TM")});
    }

    
    @Test
    public void testSorting11() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber(13));
        numbers.add(new LineNumber("6"));
        numbers.add(new LineNumber("6a"));
        numbers.add(new LineNumber("Z6"));
        numbers.add(new LineNumber("Z"));
        numbers.add(new LineNumber(2));
        numbers.add(new LineNumber("X"));
        numbers.add(new LineNumber("6b"));
        numbers.add(new LineNumber("Z7"));
        numbers.add(new LineNumber("164"));
        numbers.add(new LineNumber(192));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{
                new LineNumber(2),
                new LineNumber(6),
                new LineNumber("6a"),
                new LineNumber("6b"),
                new LineNumber(13),
                new LineNumber(164),
                new LineNumber(192),
                new LineNumber("X"),
                new LineNumber("Z"),
                new LineNumber("Z6"),
                new LineNumber("Z7")}
        );
    }


    /**
     * *************** API *******************
     */

    private void checkNumber(LineNumber lineNumber, int expectedNumeric, String expectedLiteral, boolean expectedIsNumeric) {
        assertEquals(expectedNumeric, lineNumber.getNumeric());
        assertEquals(expectedLiteral, lineNumber.getLiteral());
        assertEquals(expectedIsNumeric, lineNumber.isNumericOnly());
    }

    private void checkDifferentNumbersComparison(LineNumber smallerNumber, LineNumber biggerNumber) {
        assertTrue(smallerNumber.compareTo(biggerNumber) < 0);
        assertTrue(biggerNumber.compareTo(smallerNumber) > 0);
    }

    private void checkEqualNumbersComparison(LineNumber number1, LineNumber number2, boolean equal) {
        assertEquals(equal, number1.compareTo(number2) == 0);
    }

    private void checkNumbersList(List<LineNumber> lineNumbers, LineNumber[] expectedLineNumbers) {
        assertEquals(lineNumbers.size(), expectedLineNumbers.length);
        for (int i = 0; i < expectedLineNumbers.length; i++) {
            assertEquals(expectedLineNumbers[i], lineNumbers.get(i));
        }
    }

}
