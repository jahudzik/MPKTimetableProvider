package pl.jahu.mpk.entities;

import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public final class Timetable {

    private final String station;

    private final Line line;

    private final String destStation;

    private final DayType dayType;

    private final List<Departure> departures;


    public Timetable(String station, Line line, String destStation, DayType dayType, List<Departure> departures) {
        this.station = station;
        this.line = line;
        this.destStation = destStation;
        this.dayType = dayType;
        this.departures = departures;
    }

    public List<Departure> getDepartures() {
        return departures;
    }

    public DayType getDayType() {
        return dayType;
    }

    public String getStation() {
        return station;
    }

    public Line getLine() {
        return line;
    }

    public String getDestStation() {
        return destStation;
    }

}
