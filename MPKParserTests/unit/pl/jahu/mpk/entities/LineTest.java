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
public class LineTest {

    @Test
    public void equals_test1() {
        Line line = new Line(new LineNumber(1));
        assertTrue(line.equals(line));
    }

    @Test
    public void equals_test2() {
        Line line1 = new Line(new LineNumber(1));
        Line line2 = new Line(new LineNumber(1));
        assertTrue(line1.equals(line2));
        assertTrue(line2.equals(line1));
    }

    @Test
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    public void equals_test3() {
        Line line = new Line(new LineNumber(1));
        LineNumber lineNumber = new LineNumber(1);
        assertFalse(line.equals(lineNumber));
    }

    @Test
    public void equals_test4() {
        Line line = new Line(new LineNumber(1));
        assertFalse(line.equals(null));
    }

    @Test
    public void equals_test5() {
        Line line1 = new Line(new LineNumber(1), null);
        Line line2 = new Line(new LineNumber(1));
        assertTrue(line1.equals(line2));
        assertTrue(line2.equals(line1));
    }

    @Test
    public void equals_test6() {
        Line line1 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        Line line2 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertTrue(line1.equals(line2));
        assertTrue(line2.equals(line1));
    }

    @Test
    public void equals_test7() {
        Line line1 = new Line(new LineNumber(1), null);
        Line line2 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertFalse(line1.equals(line2));
        assertFalse(line2.equals(line1));
    }

    @Test
    public void equals_test8() {
        Line line1 = new Line(new LineNumber(1), new LineType(AreaTypes.AGGLOMERATION, ReasonTypes.STANDARD, VehicleTypes.BUS));
        Line line2 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertFalse(line1.equals(line2));
        assertFalse(line2.equals(line1));
    }

    @Test
    public void equals_test9() {
        Line line1 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        Line line2 = new Line(new LineNumber(2), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertFalse(line1.equals(line2));
        assertFalse(line2.equals(line1));
    }



    @Test
    public void hashCode_test1() {
        Line line1 = new Line(new LineNumber(1), null);
        Line line2 = new Line(new LineNumber(1));
        assertTrue(line1.equals(line2));
        assertTrue(line2.equals(line1));
    }

    @Test
    public void hashCode_test2() {
        Line line1 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        Line line2 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    public void hashCode_test3() {
        Line line1 = new Line(new LineNumber(1), null);
        Line line2 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertNotEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    public void hashCode_test4() {
        Line line1 = new Line(new LineNumber(1), new LineType(AreaTypes.AGGLOMERATION, ReasonTypes.STANDARD, VehicleTypes.BUS));
        Line line2 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertNotEquals(line1.hashCode(), line2.hashCode());
    }

    @Test
    public void hashCode_test5() {
        Line line1 = new Line(new LineNumber(1), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        Line line2 = new Line(new LineNumber(2), new LineType(AreaTypes.CITY, ReasonTypes.STANDARD, VehicleTypes.BUS));
        assertNotEquals(line1.hashCode(), line2.hashCode());
    }

}
