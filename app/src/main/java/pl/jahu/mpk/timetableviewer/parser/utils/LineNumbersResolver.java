package pl.jahu.mpk.timetableviewer.parser.utils;

import pl.jahu.mpk.timetableviewer.enums.AreaTypes;
import pl.jahu.mpk.timetableviewer.enums.ReasonTypes;
import pl.jahu.mpk.timetableviewer.enums.VehicleTypes;

import java.util.*;

/**
 * Created by jahudzik on 2014-07-19.
 */
public class LineNumbersResolver {
    
    public static final int NO_LINE_NUMBER = -1;

    private Set<AreaTypes> areaTypes;
    private Set<ReasonTypes> reasonTypes;
    private Set<VehicleTypes> vehicleTypes;

    public LineNumbersResolver(boolean empty) {
        this.areaTypes = new HashSet<AreaTypes>();
        this.reasonTypes = new HashSet<ReasonTypes>();
        this.vehicleTypes = new HashSet<VehicleTypes>();
        if (!empty) {
            this.areaTypes.add(AreaTypes.CITY);
            this.areaTypes.add(AreaTypes.AGGLOMERATION);
            this.reasonTypes.add(ReasonTypes.ADDITIONAL);
            this.reasonTypes.add(ReasonTypes.NIGHTLY);
            this.reasonTypes.add(ReasonTypes.RAPID);
            this.reasonTypes.add(ReasonTypes.NIGHTLY);
            this.reasonTypes.add(ReasonTypes.SPECIAL);
            this.reasonTypes.add(ReasonTypes.STANDARD);
            this.vehicleTypes.add(VehicleTypes.BUS);
            this.vehicleTypes.add(VehicleTypes.TRAM);
        }
    }

    public void addAreaType(AreaTypes type) {
        areaTypes.add(type);
    }

    public void addReasonType(ReasonTypes type) {
        reasonTypes.add(type);
    }

    public void addVehicleType(VehicleTypes type) {
        vehicleTypes.add(type);
    }

    public List<Integer> getLineNumbersCandidates() {
        LinesList lines = new LinesList();
        if (vehicleTypes.contains(VehicleTypes.TRAM)) {
            if (reasonTypes.contains(ReasonTypes.SPECIAL)) {
                lines.add(0);
            }
            if (reasonTypes.contains(ReasonTypes.STANDARD)) {
                lines.addRange(1, 59);
            }
            if (reasonTypes.contains(ReasonTypes.NIGHTLY)) {
                lines.addRange(60, 69);
            }
            if (reasonTypes.contains(ReasonTypes.REPLACEMENT)) {
                lines.addRange(70, 79);
            }
        }
        if (vehicleTypes.contains(VehicleTypes.BUS)) {
            if (areaTypes.contains(AreaTypes.CITY)) {
                if (reasonTypes.contains(ReasonTypes.STANDARD)) {
                    lines.addRange(100, 199);
                }
                if (reasonTypes.contains(ReasonTypes.ADDITIONAL)) {
                    lines.addRange(400, 499);
                }
                if (reasonTypes.contains(ReasonTypes.RAPID)) {
                    lines.addRange(500, 599);
                }
                if (reasonTypes.contains(ReasonTypes.NIGHTLY)) {
                    lines.addRange(600, 699);
                }
                if (reasonTypes.contains(ReasonTypes.REPLACEMENT)) {
                    lines.addRange(700, 799);
                }
            }
            if (areaTypes.contains(AreaTypes.AGGLOMERATION)) {
                if (reasonTypes.contains(ReasonTypes.STANDARD)) {
                    lines.addRange(200, 299);
                }
                if (reasonTypes.contains(ReasonTypes.RAPID)) {
                    lines.addRange(300, 399);
                }
                if (reasonTypes.contains(ReasonTypes.NIGHTLY)) {
                    lines.addRange(900, 999);
                }
            }
        }
        Collections.sort(lines);
        return lines;
    }

    private class LinesList extends ArrayList<Integer> {

        public void addRange(int a, int b) {
            for (int i = a; i <= b; i++) {
                add(i);
            }
        }

    }

}
