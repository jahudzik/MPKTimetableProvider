package pl.jahu.mpk.utils;

import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;

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
        this.areaTypes = new HashSet<>();
        this.reasonTypes = new HashSet<>();
        this.vehicleTypes = new HashSet<>();
        if (!empty) {
            this.areaTypes.add(AreaTypes.CITY);
            this.areaTypes.add(AreaTypes.AGGLOMERATION);
            this.reasonTypes.add(ReasonTypes.ADDITIONAL);
            this.reasonTypes.add(ReasonTypes.NIGHTLY);
            this.reasonTypes.add(ReasonTypes.RAPID);
            this.reasonTypes.add(ReasonTypes.NIGHTLY);
            this.reasonTypes.add(ReasonTypes.REPLACEMENT);
            this.reasonTypes.add(ReasonTypes.SPECIAL);
            this.reasonTypes.add(ReasonTypes.STANDARD);
            this.vehicleTypes.add(VehicleTypes.BUS);
            this.vehicleTypes.add(VehicleTypes.TRAM);
        }
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

    /**
     * Returns range of passed lines array containing all numbers greater or equal to firstLine and smaller or equal to lastLine
     *
     * @return array with two integers: begin index and end index in lines array, or NO_LINE_NUMBER values if no lines match
     */
    public static int[] getLinesFromRange(List<Line> lines, LineNumber firstLine, LineNumber lastLine) {
        Collections.sort(lines);

        if (lastLine.compareTo(firstLine) < 0 || lines.size() == 0 || firstLine.compareTo(lines.get(lines.size() - 1).getNumber()) > 0 || lastLine.compareTo(lines.get(0).getNumber()) < 0) {
            return new int[]{NO_LINE_NUMBER, NO_LINE_NUMBER};
        }

        int firstLineIndex = 0;
        while (lines.get(firstLineIndex).getNumber().compareTo(firstLine) < 0) {
            firstLineIndex++;
        }

        int lastLineIndex = lines.size() - 1;
        while (lines.get(lastLineIndex).getNumber().compareTo(lastLine) > 0) {
            lastLineIndex--;
        }
        return new int[]{firstLineIndex, lastLineIndex};
    }

    private class LinesList extends ArrayList<Integer> {

        public void addRange(int a, int b) {
            for (int i = a; i <= b; i++) {
                add(i);
            }
        }

    }

}
