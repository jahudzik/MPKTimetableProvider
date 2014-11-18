package pl.jahu.mpk.entities;

import pl.jahu.mpk.enums.Areas;
import pl.jahu.mpk.enums.LineTypes;
import pl.jahu.mpk.enums.Vehicles;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-17.
 */
public final class LineInfo {

    private final Areas area;

    private final LineTypes lineType;

    private final Vehicles vehicle;

    public LineInfo(Vehicles vehicle, LineTypes lineType, Areas area) {
        if (area == null || lineType == null || vehicle == null) {
            throw new IllegalArgumentException("Null parameter in constructor");
        }
        this.area = area;
        this.lineType = lineType;
        this.vehicle = vehicle;
    }

    public Areas getArea() {
        return area;
    }

    public LineTypes getLineType() {
        return lineType;
    }

    public Vehicles getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LineInfo) {
            LineInfo other = (LineInfo)obj;
            return (area == other.area && lineType == other.lineType && vehicle == other.vehicle);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + area.hashCode();
        result = 31 * result + lineType.hashCode();
        result = 31 * result + vehicle.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" + vehicle.toString() + ", " + lineType.toString() + ", " + area.toString() + "}";
    }
}
