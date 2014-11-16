package pl.jahu.mpk.parsers.data;

import pl.jahu.mpk.entities.LineNumber;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public final class StationData {

    private final String name;

    private final LineNumber lineNumber;

    private final int sequenceNumber;

    public StationData(String name, LineNumber lineNumber, int sequenceNumber) {
        this.name = name;
        this.lineNumber = lineNumber;
        this.sequenceNumber = sequenceNumber;
    }

    public String getName() {
        return name;
    }

    public LineNumber getLineNumber() {
        return lineNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

}
