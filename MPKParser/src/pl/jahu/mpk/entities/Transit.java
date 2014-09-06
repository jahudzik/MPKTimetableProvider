package pl.jahu.mpk.entities;

import pl.jahu.mpk.utils.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class Transit {

    private LineNumber line;
    private String destStation;
    private List<TransitStop> stops;
    private Comparator<TransitStop> stopsComparator;

    public Transit(LineNumber line) {
        this.line = line;
        stops = new ArrayList<TransitStop>();
        stopsComparator = new TransitStopsComparator();
    }

    public void addStop(TransitStop newStop) {
        stops.add(newStop);
        if (stops.size() >= 2 && stops.get(stops.size() - 2).getTime().compareDaytimeTo(newStop.getTime()) > 0) {
            // if added stop time is earlier than the last stop in the list, sort it
            Collections.sort(stops, stopsComparator);
        }
    }

    public List<TransitStop> getStops() {
        return stops;
    }

    public LineNumber getLine() {
        return line;
    }

    public String getDestStation() {
        return destStation;
    }

    public void setDestStation(String destStation) {
        this.destStation = destStation;
    }

    public Time getLastStopTime() {
        return (stops.size() > 0 ) ? stops.get(stops.size() - 1).getTime() : null;
    }

    public String getDirections() {
        if (stops.size() == 0) {
            return "[empty]";
        }
        return stops.get(0).toString() + " -> " + stops.get(stops.size() - 1).toString() + " -> " + destStation;
    }

    public int getDuration() {
        return getLastStopTime().compareDaytimeTo(stops.get(0).getTime());
    }

    @Override
    public String toString() {
        if (stops.size() == 0) {
            return "[empty]";
        }
        StringBuilder sb = new StringBuilder();
        for (TransitStop stop : stops) {
            sb.append(stop).append(" ");
        }
        sb.append("-> ").append(destStation);
        return sb.toString();
    }

    private class TransitStopsComparator implements Comparator<TransitStop> {
        @Override
        public int compare(TransitStop o1, TransitStop o2) {
            return o1.getTime().compareDaytimeTo(o2.getTime());
        }
    }

}
