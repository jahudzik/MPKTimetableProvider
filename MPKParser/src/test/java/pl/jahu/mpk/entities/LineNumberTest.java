package pl.jahu.mpk.entities;

import org.junit.Test;

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

    @Test(expected = IllegalArgumentException.class)
    public void constructor_literal1() {
        new LineNumber("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_literal2() {
        new LineNumber(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_literal3() {
        new LineNumber("12345");
    }

    @Test
    public void constructor_literal4() {
        checkNumber(new LineNumber(16), 16, "16");
    }

    @Test
    public void constructor_literal5() {
        checkNumber(new LineNumber("Z8"), 8, "Z8");
    }

    @Test
    public void constructor_literal6() {
        checkNumber(new LineNumber("62A"), 62, "62A");
    }

    @Test
    public void constructor_literal7() {
        checkNumber(new LineNumber("D"), -1, "D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_numeric1() {
        new LineNumber(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructor_numeric2() {
        new LineNumber(10771);
    }

    @Test
    public void constructor_numeric3() {
        checkNumber(new LineNumber(3), 3, "3");
    }


    @Test
    public void compareTo_test1() {
        checkDifferentNumbersComparison(new LineNumber("3"), new LineNumber("7"));
    }

    @Test
    public void compareTo_test2() {
        checkDifferentNumbersComparison(new LineNumber("3A"), new LineNumber("7"));
    }

    @Test
    public void compareTo_test3() {
        checkDifferentNumbersComparison(new LineNumber("3"), new LineNumber("3B"));
    }

    @Test
    public void compareTo_test4() {
        checkDifferentNumbersComparison(new LineNumber("B"), new LineNumber("C2"));
    }


    @Test
    public void compareTo_test5() {
        checkDifferentNumbersComparison(new LineNumber("D"), new LineNumber("L"));
    }

    @Test
    public void compareTo_test6() {
        checkDifferentNumbersComparison(new LineNumber("5A"), new LineNumber("5B"));
    }

    @Test
    public void compareTo_test7() {
        checkEqualNumbersComparison(new LineNumber("5"), new LineNumber("5"), true);
    }

    @Test
    public void compareTo_test8() {
        checkEqualNumbersComparison(new LineNumber(5), new LineNumber("5"), true);
    }

    @Test
    public void compareTo_test9() {
        checkEqualNumbersComparison(new LineNumber("5"), new LineNumber("5A"), false);
    }

    @Test
    public void sort_test1() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber(13));
        numbers.add(new LineNumber("6"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(6), new LineNumber(13)});
    }

    @Test
    public void sort_test2() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("6a"));
        numbers.add(new LineNumber("6"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(6), new LineNumber("6a")});
    }

    @Test
    public void sort_test3() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber(13));
        numbers.add(new LineNumber("Z6"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(13), new LineNumber("Z6")});
    }


    @Test
    public void sort_test4() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber(16));
        numbers.add(new LineNumber("X"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber(16), new LineNumber("X")});
    }

    @Test
    public void sort_test5() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("8c"));
        numbers.add(new LineNumber("8e"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("8c"), new LineNumber("8e")});
    }

    @Test
    public void sort_test6() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("8c"));
        numbers.add(new LineNumber("E8"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("8c"), new LineNumber("E8")});
    }

    @Test
    public void sort_test7() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("A"));
        numbers.add(new LineNumber("8e"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("8e"), new LineNumber("A")});
    }

    @Test
    public void sort_test8() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("Z2"));
        numbers.add(new LineNumber("Z1"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("Z1"), new LineNumber("Z2")});
    }

    @Test
    public void sort_test9() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("M"));
        numbers.add(new LineNumber("M4"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("M"), new LineNumber("M4")});
    }

    @Test
    public void sort_test10() {
        List<LineNumber> numbers = new ArrayList<>();
        numbers.add(new LineNumber("TM"));
        numbers.add(new LineNumber("P"));

        Collections.sort(numbers);
        checkNumbersList(numbers, new LineNumber[]{new LineNumber("P"), new LineNumber("TM")});
    }

    
    @Test
    public void sort_test11() {
        List<LineNumber> numbers = new ArrayList<>();
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

    @Test
    public void equals_test1() {
        LineNumber lineNumber = new LineNumber(4);
        assertFalse(lineNumber.equals(null));
    }

    @Test
    public void equals_test2() {
        LineNumber lineNumber = new LineNumber(4);
        assertTrue(lineNumber.equals(lineNumber));
    }

    @Test
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equals_test3() {
        LineNumber lineNumber = new LineNumber(4);
        String literal = "some string";
        assertFalse(lineNumber.equals(literal));
    }

    @Test
    public void equals_test4() {
        LineNumber lineNumber1 = new LineNumber(4);
        LineNumber lineNumber2 = new LineNumber("4");
        assertTrue(lineNumber1.equals(lineNumber2));
        assertTrue(lineNumber2.equals(lineNumber1));
    }

    @Test
    public void equals_test5() {
        LineNumber lineNumber1 = new LineNumber("4");
        LineNumber lineNumber2 = new LineNumber("41");
        assertFalse(lineNumber1.equals(lineNumber2));
        assertFalse(lineNumber2.equals(lineNumber1));
    }

    @Test
    public void equals_test6() {
        LineNumber lineNumber1 = new LineNumber(4);
        LineNumber lineNumber2 = new LineNumber(41);
        assertFalse(lineNumber1.equals(lineNumber2));
        assertFalse(lineNumber2.equals(lineNumber1));
    }

    @Test
    public void hashCode_test1() {
        LineNumber lineNumber1 = new LineNumber(4);
        LineNumber lineNumber2 = new LineNumber(4);
        assertEquals(lineNumber1.hashCode(), lineNumber2.hashCode());
    }

    @Test
    public void hashCode_test2() {
        LineNumber lineNumber1 = new LineNumber(4);
        LineNumber lineNumber2 = new LineNumber("4");
        assertEquals(lineNumber1.hashCode(), lineNumber2.hashCode());
    }

    @Test
    public void hashCode_test3() {
        LineNumber lineNumber1 = new LineNumber(4);
        LineNumber lineNumber2 = new LineNumber(41);
        assertNotEquals(lineNumber1.hashCode(), lineNumber2.hashCode());
    }

    @Test
    public void toString_test1() {
        LineNumber lineNumber = new LineNumber("69A");
        assertEquals("69A", lineNumber.toString());
    }

    @Test
    public void toString_test2() {
        LineNumber lineNumber = new LineNumber(14);
        assertEquals("14", lineNumber.toString());
    }


    /***************** API ********************/

    private void checkNumber(LineNumber lineNumber, int expectedNumeric, String expectedLiteral) {
        assertEquals(expectedNumeric, lineNumber.getNumeric());
        assertEquals(expectedLiteral, lineNumber.getValue());
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
