package pl.jahu.mpk.entities;

import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-17.
 */
public final class LineType {

    private final AreaTypes areaType;

    private final ReasonTypes reasonType;

    private final VehicleTypes vehicleType;

    public LineType(VehicleTypes vehicleType, ReasonTypes reasonType, AreaTypes areaType) {
        if (areaType == null || reasonType == null || vehicleType == null) {
            throw new IllegalArgumentException("Null parameter in constructor");
        }
        this.areaType = areaType;
        this.reasonType = reasonType;
        this.vehicleType = vehicleType;
    }

    public AreaTypes getAreaType() {
        return areaType;
    }

    public ReasonTypes getReasonType() {
        return reasonType;
    }

    public VehicleTypes getVehicleType() {
        return vehicleType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof LineType) {
            LineType other = (LineType)obj;
            return (areaType == other.areaType && reasonType == other.reasonType && vehicleType == other.vehicleType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + areaType.hashCode();
        result = 31 * result + reasonType.hashCode();
        result = 31 * result + vehicleType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "{" + vehicleType.toString() + ", " + reasonType.toString() + ", " + areaType.toString() + "}";
    }
}
