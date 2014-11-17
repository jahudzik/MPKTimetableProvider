package pl.jahu.mpk.entities;

import org.junit.Test;
import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;

import static org.junit.Assert.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-17.
 */
public class LineTypeTest {

    @Test
    public void equals_test1() {
        LineType lineType = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        assertTrue(lineType.equals(lineType));
    }

    @Test
    public void equals_test2() {
        LineType lineType1 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        LineType lineType2 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        assertTrue(lineType1.equals(lineType2));
        assertTrue(lineType2.equals(lineType1));
    }

    @Test
    public void equals_test3() {
        LineType lineType = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        assertFalse(lineType.equals(null));
    }

    @Test
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equals_test4() {
        LineType lineType = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        LineNumber lineNumber = new LineNumber(9);
        assertFalse(lineType.equals(lineNumber));
    }

    @Test
    public void equals_test5() {
        LineType lineType1 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        LineType lineType2 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.AGGLOMERATION);
        assertFalse(lineType1.equals(lineType2));
        assertFalse(lineType2.equals(lineType1));
    }

    @Test
    public void equals_test6() {
        LineType lineType1 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        LineType lineType2 = new LineType(VehicleTypes.TRAM, ReasonTypes.STANDARD, AreaTypes.CITY);
        assertFalse(lineType1.equals(lineType2));
        assertFalse(lineType2.equals(lineType1));
    }



    @Test
    public void hashCode_test1() {
        LineType lineType1 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        LineType lineType2 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        assertEquals(lineType1.hashCode(), lineType2.hashCode());
    }


    @Test
    public void hashCode_test2() {
        LineType lineType1 = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);
        LineType lineType2 = new LineType(VehicleTypes.TRAM, ReasonTypes.STANDARD, AreaTypes.CITY);
        assertNotEquals(lineType1.hashCode(), lineType2.hashCode());
    }


}
