package pl.jahu.mpk.entities;

import org.junit.Test;
import pl.jahu.mpk.enums.Areas;
import pl.jahu.mpk.enums.LineTypes;
import pl.jahu.mpk.enums.Vehicles;

import static org.junit.Assert.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-17.
 */
public class LineInfoTest {

    @Test
    public void equals_test1() {
        LineInfo lineInfo = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        assertTrue(lineInfo.equals(lineInfo));
    }

    @Test
    public void equals_test2() {
        LineInfo lineInfo1 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        LineInfo lineInfo2 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        assertTrue(lineInfo1.equals(lineInfo2));
        assertTrue(lineInfo2.equals(lineInfo1));
    }

    @Test
    public void equals_test3() {
        LineInfo lineInfo = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        assertFalse(lineInfo.equals(null));
    }

    @Test
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equals_test4() {
        LineInfo lineInfo = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        LineNumber lineNumber = new LineNumber(9);
        assertFalse(lineInfo.equals(lineNumber));
    }

    @Test
    public void equals_test5() {
        LineInfo lineInfo1 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        LineInfo lineInfo2 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.AGGLOMERATION);
        assertFalse(lineInfo1.equals(lineInfo2));
        assertFalse(lineInfo2.equals(lineInfo1));
    }

    @Test
    public void equals_test6() {
        LineInfo lineInfo1 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        LineInfo lineInfo2 = new LineInfo(Vehicles.TRAM, LineTypes.STANDARD, Areas.CITY);
        assertFalse(lineInfo1.equals(lineInfo2));
        assertFalse(lineInfo2.equals(lineInfo1));
    }



    @Test
    public void hashCode_test1() {
        LineInfo lineInfo1 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        LineInfo lineInfo2 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        assertEquals(lineInfo1.hashCode(), lineInfo2.hashCode());
    }


    @Test
    public void hashCode_test2() {
        LineInfo lineInfo1 = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);
        LineInfo lineInfo2 = new LineInfo(Vehicles.TRAM, LineTypes.STANDARD, Areas.CITY);
        assertNotEquals(lineInfo1.hashCode(), lineInfo2.hashCode());
    }


}
