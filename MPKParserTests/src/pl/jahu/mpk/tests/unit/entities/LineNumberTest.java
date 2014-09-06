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

    @Test(expected = UnsupportedLineNumberException.class)
    public void testLiteralConstructor1() throws UnsupportedLineNumberException {
        new LineNumber("");
    }

    @Test(expected = UnsupportedLineNumberException.class)
    public void testLiteralConstructor2() throws UnsupportedLineNumberException {
        new LineNumber(null);
    }

    @Test
    public void testLiteralConstructor3() throws UnsupportedLineNumberException {
        LineNumber lineNumber = new LineNumber("16");
        assertEquals(16, lineNumber.getNumeric());
        assertEquals("16", lineNumber.getLiteral());
        assertTrue(lineNumber.isNumericOnly());
    }

    @Test
    public void testLiteralConstructor4() throws UnsupportedLineNumberException {
        LineNumber lineNumber = new LineNumber("Z8");
        assertEquals(8, lineNumber.getNumeric());
        assertEquals("Z8", lineNumber.getLiteral());
        assertFalse(lineNumber.isNumericOnly());
    }

    @Test
    public void testLiteralConstructor5() throws UnsupportedLineNumberException {
        LineNumber lineNumber = new LineNumber("62A");
        assertEquals(62, lineNumber.getNumeric());
        assertEquals("62A", lineNumber.getLiteral());
        assertFalse(lineNumber.isNumericOnly());
    }


    @Test
    public void testLiteralConstructor6() throws UnsupportedLineNumberException {
        LineNumber lineNumber = new LineNumber("D");
        assertEquals(-1, lineNumber.getNumeric());
        assertEquals("D", lineNumber.getLiteral());
        assertFalse(lineNumber.isNumericOnly());
    }

    @Test(expected = UnsupportedLineNumberException.class)
    public void testNumericConstructor1() throws UnsupportedLineNumberException {
        new LineNumber(-1);
    }

    @Test
    public void testNumericConstructor2() throws UnsupportedLineNumberException {
        LineNumber lineNumber = new LineNumber(3);
        assertEquals(3, lineNumber.getNumeric());
        assertEquals("3", lineNumber.getLiteral());
        assertTrue(lineNumber.isNumericOnly());
    }


    @Test
    public void testComparator1() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("3");
        LineNumber lineNumber2 = new LineNumber("7");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator2() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("3A");
        LineNumber lineNumber2 = new LineNumber("7");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator3() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("3");
        LineNumber lineNumber2 = new LineNumber("3B");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator4() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("B");
        LineNumber lineNumber2 = new LineNumber("C2");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }


    @Test
    public void testComparator5() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("D");
        LineNumber lineNumber2 = new LineNumber("L");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator6() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("5A");
        LineNumber lineNumber2 = new LineNumber("5B");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testEquals1() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("5");
        LineNumber lineNumber2 = new LineNumber("5");
        assertTrue(lineNumber1.equals(lineNumber2));
    }

    @Test
    public void testEquals2() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber(5);
        LineNumber lineNumber2 = new LineNumber("5");
        assertTrue(lineNumber1.equals(lineNumber2));
    }


    @Test
    public void testEquals3() throws UnsupportedLineNumberException {
        LineNumber lineNumber1 = new LineNumber("5A");
        LineNumber lineNumber2 = new LineNumber("5");
        assertFalse(lineNumber1.equals(lineNumber2));
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

        assertEquals(new LineNumber(6), numbers.get(0));
        assertEquals(new LineNumber(13), numbers.get(1));
    }

    @Test
    public void testSorting2() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("6a"));
        numbers.add(new LineNumber("6"));

        Collections.sort(numbers);

        assertEquals(new LineNumber(6), numbers.get(0));
        assertEquals(new LineNumber("6a"), numbers.get(1));
    }

    @Test
    public void testSorting3() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber(13));
        numbers.add(new LineNumber("Z6"));

        Collections.sort(numbers);

        assertEquals(new LineNumber(13), numbers.get(0));
        assertEquals(new LineNumber("Z6"), numbers.get(1));
    }


    @Test
    public void testSorting4() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber(16));
        numbers.add(new LineNumber("X"));

        Collections.sort(numbers);

        assertEquals(new LineNumber(16), numbers.get(0));
        assertEquals(new LineNumber("X"), numbers.get(1));
    }

    @Test
    public void testSorting5() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("8c"));
        numbers.add(new LineNumber("8e"));

        Collections.sort(numbers);

        assertEquals(new LineNumber("8c"), numbers.get(0));
        assertEquals(new LineNumber("8e"), numbers.get(1));
    }

    @Test
    public void testSorting6() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("8c"));
        numbers.add(new LineNumber("E8"));

        Collections.sort(numbers);

        assertEquals(new LineNumber("8c"), numbers.get(0));
        assertEquals(new LineNumber("E8"), numbers.get(1));
    }

    @Test
    public void testSorting7() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("A"));
        numbers.add(new LineNumber("8e"));

        Collections.sort(numbers);

        assertEquals(new LineNumber("8e"), numbers.get(0));
        assertEquals(new LineNumber("A"), numbers.get(1));
    }

    @Test
    public void testSorting8() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("Z2"));
        numbers.add(new LineNumber("Z1"));

        Collections.sort(numbers);

        assertEquals(new LineNumber("Z1"), numbers.get(0));
        assertEquals(new LineNumber("Z2"), numbers.get(1));
    }

    @Test
    public void testSorting9() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("M"));
        numbers.add(new LineNumber("M4"));

        Collections.sort(numbers);

        assertEquals(new LineNumber("M"), numbers.get(0));
        assertEquals(new LineNumber("M4"), numbers.get(1));
    }

    @Test
    public void testSorting10() throws UnsupportedLineNumberException {
        List<LineNumber> numbers = new ArrayList<LineNumber>();
        numbers.add(new LineNumber("TM"));
        numbers.add(new LineNumber("P"));

        Collections.sort(numbers);

        assertEquals(new LineNumber("P"), numbers.get(0));
        assertEquals(new LineNumber("TM"), numbers.get(1));
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

        assertEquals(new LineNumber(2), numbers.get(0));
        assertEquals(new LineNumber(6), numbers.get(1));
        assertEquals(new LineNumber("6a"), numbers.get(2));
        assertEquals(new LineNumber("6b"), numbers.get(3));
        assertEquals(new LineNumber(13), numbers.get(4));
        assertEquals(new LineNumber(164), numbers.get(5));
        assertEquals(new LineNumber(192), numbers.get(6));
        assertEquals(new LineNumber("X"), numbers.get(7));
        assertEquals(new LineNumber("Z"), numbers.get(8));
        assertEquals(new LineNumber("Z6"), numbers.get(9));
        assertEquals(new LineNumber("Z7"), numbers.get(10));
    }



}
