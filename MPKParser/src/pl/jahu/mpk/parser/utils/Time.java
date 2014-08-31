package pl.jahu.mpk.parser.utils;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-30.
 */
public class Time implements Comparable<Time> {

    private static final int DAY_MINUTES_COUNT = 24 * 60;

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

    public int getTimeValue() {
        return 60 * hour + min;
    }

    /**
     * Compares this time value to passed time's value.
     * Assumes both are day times, which means 00:xx is later than 23:xx.
     * Returns value less than 0, if this time is earlier than passed time, bigger than 0, if it's later, equal to 0 otherwise.
     *
     */
    public int compareDaytimeTo(Time o) {
        int timeValue1 = (hour == 0) ? getTimeValue() + DAY_MINUTES_COUNT : getTimeValue();
        int timeValue2 = (o.getHour() == 0) ? o.getTimeValue() + DAY_MINUTES_COUNT : o.getTimeValue();
        return (timeValue1 - timeValue2);
    }

    @Override
    public int compareTo(Time o) {
        int diff = getTimeValue() - o.getTimeValue();
        if (Math.abs(diff) > DAY_MINUTES_COUNT / 2) {
            // difference between times is bigger than half a day = assume one of them is from the next day (ex. 23:50 & 00:10)
            diff = (diff > 0) ? diff - DAY_MINUTES_COUNT : diff + DAY_MINUTES_COUNT;
        }
        return diff;
    }

    @Override
    public String toString() {
        return ((hour < 10) ? "0" + hour : hour) + ":" + ((min < 10) ? "0" + min : min);
    }

}
