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
        LineType lineType = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        assertTrue(lineType.equals(lineType));
    }

    @Test
    public void equals_test2() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        assertTrue(lineType1.equals(lineType2));
        assertTrue(lineType2.equals(lineType1));
    }

    @Test
    public void equals_test3() {
        LineType lineType = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        assertFalse(lineType.equals(null));
    }

    @Test
    public void equals_test4() {
        LineType lineType = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineNumber lineNumber = new LineNumber(9);
        assertFalse(lineType.equals(lineNumber));
    }

    @Test
    public void equals_test5() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.AGGLOMERATION, ReasonTypes.STANDARD, VehicleTypes.BUS);
        assertFalse(lineType1.equals(lineType2));
        assertFalse(lineType2.equals(lineType1));
    }

    @Test
    public void equals_test6() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.TRAM);
        assertFalse(lineType1.equals(lineType2));
        assertFalse(lineType2.equals(lineType1));
    }

    @Test
    public void equals_test7() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, null);
        assertFalse(lineType1.equals(lineType2));
        assertFalse(lineType2.equals(lineType1));
    }


    @Test
    public void hashCode_test1() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        assertEquals(lineType1.hashCode(), lineType2.hashCode());
    }

    @Test
    public void hashCode_test2() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, null);
        assertNotEquals(lineType1.hashCode(), lineType2.hashCode());
    }

    @Test
    public void hashCode_test3() {
        LineType lineType1 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS);
        LineType lineType2 = new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.TRAM);
        assertNotEquals(lineType1.hashCode(), lineType2.hashCode());
    }


}
