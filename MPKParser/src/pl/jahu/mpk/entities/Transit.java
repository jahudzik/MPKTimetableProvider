package pl.jahu.mpk.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class Transit {

    private int line;
    private List<TransitStop> stops;
    private Comparator<TransitStop> stopsComparator;

    public Transit(int line) {
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

    public int getLine() {
        return line;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TransitStop stop : stops) {
            sb.append(stop.getStation() + " [" + stop.getTime() + "] ");
        }
        return sb.toString();
    }

    private class TransitStopsComparator implements Comparator<TransitStop> {
        @Override
        public int compare(TransitStop o1, TransitStop o2) {
            return o1.getTime().compareDaytimeTo(o2.getTime());
        }
    }

}
