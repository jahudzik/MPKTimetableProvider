package pl.jahu.mpk.entities;

import pl.jahu.mpk.utils.Time;

/**
 * Created by jahudzik on 2014-07-13.
 */
public final class Departure {

    private final Time time;
    private final String[] extraInfo;

    public Departure(int hour, int min, String[] extraInfo) {
        this.time = new Time(hour, min);
        this.extraInfo = extraInfo;
    }

    public int getHour() {
        return time.getHour();
    }

    public int getMin() {
        return time.getMin();
    }

    public Time getTime() {
        return time;
    }

    public String[] getExtraInfo() {
        return extraInfo;
    }

}
