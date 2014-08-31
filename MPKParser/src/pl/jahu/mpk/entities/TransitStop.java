package pl.jahu.mpk.entities;

import pl.jahu.mpk.parser.utils.Time;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class TransitStop {

    private Time time;
    private String station;

    public TransitStop(Time time, String station) {
        this.time = time;
        this.station = station;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public String toString() {
        return station + " [" + time + "]";
    }
}
