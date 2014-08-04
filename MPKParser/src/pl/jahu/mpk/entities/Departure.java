package pl.jahu.mpk.entities;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class Departure {

    private int hour;
    private int min;
    private String[] extraInfo;

    public Departure(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public Departure(int hour, int min, String[] extraInfo) {
        this(hour, min);
        this.extraInfo = extraInfo;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String[] getExtraInfo() {
        return extraInfo;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append((hour < 10) ? "0" + hour : hour);
        sb.append(":");
        sb.append((min < 10) ? "0" + min : min);
        return sb.toString();
    }
}
