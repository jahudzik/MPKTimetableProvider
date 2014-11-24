package pl.jahu.mpk.entities;

import pl.jahu.mpk.utils.Time;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public final class TransitStop {

    private final Time time;

    private final String station;

    public TransitStop(Time time, String station) {
        this.time = time;
        this.station = station;
    }

    public Time getTime() {
        return time;
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return station + " [" + time + "]";
    }
}
