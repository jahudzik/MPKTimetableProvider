package pl.jahu.mpk.entities;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-17.
 */
public final class Line implements Comparable<Line> {

    private final LineNumber number;

    private final LineType type;

    public Line(LineNumber number) {
        this(number, null);
    }

    public Line(LineNumber number, LineType type) {
        this.number = number;
        this.type = type;
    }

    public LineNumber getNumber() {
        return number;
    }

    public LineType getType() {
        return type;
    }

    @Override
    public int compareTo(Line otherLine) {
        return number.compareTo(otherLine.getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Line) {
            Line other = (Line)obj;
            return number.equals(other.getNumber()) && ((type != null) ? type.equals(other.getType()) : other.getType() == null);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + number.hashCode();
        result = 31 * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "{number=" + number + ", type=" + type + "}";
    }

}
