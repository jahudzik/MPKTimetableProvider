package pl.jahu.mpk.entities;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-17.
 */
public final class Line implements Comparable<Line> {

    private final LineNumber number;
    private final LineInfo type;

    public Line(int numberValue, LineInfo type) {
        this.number = new LineNumber(numberValue);
        this.type = type;
    }

    public Line(String numberValue, LineInfo type) {
        this.number = new LineNumber(numberValue);
        this.type = type;
    }

    public LineNumber getNumber() {
        return number;
    }

    public LineInfo getType() {
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
