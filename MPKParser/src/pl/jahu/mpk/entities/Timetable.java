package pl.jahu.mpk.entities;

import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class Timetable {

    private String station;
    private LineNumber line;
    private String destStation;
    private DayType dayType;
    private List<Departure> departures;

    public Timetable(String station, LineNumber line, String destStation, DayType dayType, List<Departure> departures) {
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

    public LineNumber getLine() {
        return line;
    }

    public String getDestStation() {
        return destStation;
    }
}
