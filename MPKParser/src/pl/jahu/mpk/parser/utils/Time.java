package pl.jahu.mpk.parser.utils;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-30.
 */
public class Time implements Comparable<Time> {

    private int hour;
    private int min;

    public Time(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getTime() {
        return 60 * hour + min;
    }

    @Override
    public int compareTo(Time o) {
        return getTime() - o.getTime();
    }

    @Override
    public String toString() {
        return ((hour < 10) ? "0" + hour : hour) + ":" + ((min < 10) ? "0" + min : min);
    }

}
