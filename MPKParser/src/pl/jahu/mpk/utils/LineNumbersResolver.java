package pl.jahu.mpk.utils;

import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.enums.Areas;
import pl.jahu.mpk.enums.LineTypes;
import pl.jahu.mpk.enums.Vehicles;

import java.util.*;

/**
 * Created by jahudzik on 2014-07-19.
 */
public class LineNumbersResolver {
    
    public static final int NO_LINE_NUMBER = -1;

    private Set<Areas> areas;
    private Set<LineTypes> lineTypes;
    private Set<Vehicles> vehicles;

    public LineNumbersResolver(boolean empty) {
        this.areas = new HashSet<>();
        this.lineTypes = new HashSet<>();
        this.vehicles = new HashSet<>();
        if (!empty) {
            this.areas.add(Areas.CITY);
            this.areas.add(Areas.AGGLOMERATION);
            this.lineTypes.add(LineTypes.ADDITIONAL);
            this.lineTypes.add(LineTypes.NIGHTLY);
            this.lineTypes.add(LineTypes.RAPID);
            this.lineTypes.add(LineTypes.NIGHTLY);
            this.lineTypes.add(LineTypes.REPLACEMENT);
            this.lineTypes.add(LineTypes.SPECIAL);
            this.lineTypes.add(LineTypes.STANDARD);
            this.vehicles.add(Vehicles.BUS);
            this.vehicles.add(Vehicles.TRAM);
        }
    }


    public List<Integer> getLineNumbersCandidates() {
        LinesList lines = new LinesList();
        if (vehicles.contains(Vehicles.TRAM)) {
            if (lineTypes.contains(LineTypes.STANDARD)) {
                lines.addRange(1, 59);
            }
            if (lineTypes.contains(LineTypes.NIGHTLY)) {
                lines.addRange(60, 69);
            }
            if (lineTypes.contains(LineTypes.REPLACEMENT)) {
                lines.addRange(70, 79);
            }
            if (lineTypes.contains(LineTypes.SPECIAL)) {
                lines.addRange(80, 89);
                lines.add(0);
            }
        }
        if (vehicles.contains(Vehicles.BUS)) {
            if (areas.contains(Areas.CITY)) {
                if (lineTypes.contains(LineTypes.STANDARD)) {
                    lines.addRange(100, 199);
                }
                if (lineTypes.contains(LineTypes.ADDITIONAL)) {
                    lines.addRange(400, 499);
                }
                if (lineTypes.contains(LineTypes.RAPID)) {
                    lines.addRange(500, 599);
                }
                if (lineTypes.contains(LineTypes.NIGHTLY)) {
                    lines.addRange(600, 699);
                }
                if (lineTypes.contains(LineTypes.REPLACEMENT)) {
                    lines.addRange(700, 799);
                }
                if (lineTypes.contains(LineTypes.SPECIAL)) {
                    lines.addRange(800, 899);
                }
            }
            if (areas.contains(Areas.AGGLOMERATION)) {
                if (lineTypes.contains(LineTypes.STANDARD)) {
                    lines.addRange(200, 299);
                }
                if (lineTypes.contains(LineTypes.RAPID)) {
                    lines.addRange(300, 399);
                }
                if (lineTypes.contains(LineTypes.NIGHTLY)) {
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
    public static int[] getLinesFromRange(List<Line> lines, LineNumber firstLineNumber, LineNumber lastLineNumber) {
        Collections.sort(lines);

        if (lastLineNumber.compareTo(firstLineNumber) < 0 || lines.size() == 0 || firstLineNumber.compareTo(lines.get(lines.size() - 1).getNumber()) > 0 || lastLineNumber.compareTo(lines.get(0).getNumber()) < 0) {
            return new int[]{NO_LINE_NUMBER, NO_LINE_NUMBER};
        }

        int firstLineIndex = 0;
        while (lines.get(firstLineIndex).getNumber().compareTo(firstLineNumber) < 0) {
            firstLineIndex++;
        }

        int lastLineIndex = lines.size() - 1;
        while (lines.get(lastLineIndex).getNumber().compareTo(lastLineNumber) > 0) {
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
