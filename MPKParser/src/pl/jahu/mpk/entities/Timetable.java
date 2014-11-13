package pl.jahu.mpk.entities;

import java.util.List;
import java.util.Map;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class Timetable {

    private String station;
    private LineNumber line;
    private String destStation;
    private Map<DayType, List<Departure>> departures;

    public Timetable(String station, LineNumber line, String destStation, Map<DayType, List<Departure>> departures) {
        this.station = station;
        this.line = line;
        this.destStation = destStation;
        this.departures = departures;
    }

    public Map<DayType, List<Departure>> getDepartures() {
        return departures;
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
