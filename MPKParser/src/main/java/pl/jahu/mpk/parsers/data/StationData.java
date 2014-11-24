package pl.jahu.mpk.parsers.data;

import pl.jahu.mpk.entities.Line;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public final class StationData {

    private final String name;

    private final Line line;

    private final int sequenceNumber;

    public StationData(String name, Line line, int sequenceNumber) {
        this.name = name;
        this.line = line;
        this.sequenceNumber = sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

}
