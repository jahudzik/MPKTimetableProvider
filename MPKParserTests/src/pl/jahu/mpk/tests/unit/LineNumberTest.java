package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;

import static org.junit.Assert.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-06.
 */
public class LineNumberTest {

    @Test(expected = NoDataProvidedException.class)
    public void testConstructor1() throws NoDataProvidedException {
        new LineNumber("");
    }

    @Test(expected = NoDataProvidedException.class)
    public void testConstructor2() throws NoDataProvidedException {
        new LineNumber(null);
    }

    @Test
    public void testConstructor3() throws NoDataProvidedException {
         LineNumber lineNumber = new LineNumber("16");
         assertEquals(16, lineNumber.getNumeric());
         assertEquals("16", lineNumber.getLiteral());
     }

    @Test
    public void testConstructor4() throws NoDataProvidedException {
         LineNumber lineNumber = new LineNumber("Z8");
         assertEquals(8, lineNumber.getNumeric());
         assertEquals("Z8", lineNumber.getLiteral());
    }

    @Test
    public void testConstructor5() throws NoDataProvidedException {
         LineNumber lineNumber = new LineNumber("62A");
         assertEquals(62, lineNumber.getNumeric());
         assertEquals("62A", lineNumber.getLiteral());
    }


    @Test
    public void testConstructor6() throws NoDataProvidedException {
        LineNumber lineNumber = new LineNumber("D");
        assertEquals(-1, lineNumber.getNumeric());
        assertEquals("D", lineNumber.getLiteral());
    }

    @Test
    public void testComparator1() throws NoDataProvidedException {
        LineNumber lineNumber1 = new LineNumber("3");
        LineNumber lineNumber2 = new LineNumber("7");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator2() throws NoDataProvidedException {
        LineNumber lineNumber1 = new LineNumber("3A");
        LineNumber lineNumber2 = new LineNumber("7");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator3() throws NoDataProvidedException {
        LineNumber lineNumber1 = new LineNumber("3");
        LineNumber lineNumber2 = new LineNumber("3B");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator4() throws NoDataProvidedException {
        LineNumber lineNumber1 = new LineNumber("B");
        LineNumber lineNumber2 = new LineNumber("C2");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }


    @Test
    public void testComparator5() throws NoDataProvidedException {
        LineNumber lineNumber1 = new LineNumber("D");
        LineNumber lineNumber2 = new LineNumber("L");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }

    @Test
    public void testComparator6() throws NoDataProvidedException {
        LineNumber lineNumber1 = new LineNumber("5A");
        LineNumber lineNumber2 = new LineNumber("5B");
        assertTrue(lineNumber1.compareTo(lineNumber2) < 0);
        assertTrue(lineNumber2.compareTo(lineNumber1) > 0);
    }


}
