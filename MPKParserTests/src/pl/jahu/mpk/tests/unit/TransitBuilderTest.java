package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.TransitBuilder;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.entities.Transit;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-01.
 */
public class TransitBuilderTest {


    @Test
    public void testNullInput() {
        Map<DayTypes, List<Transit>> transits = TransitBuilder.buildFromTimetables(null);
        assertNotNull(transits);
        assertEquals(0, transits.size());
    }


    @Test
    public void testRegularTimetables() {
        String destStation = "Last Station";
        List<Timetable> timetables = new ArrayList<Timetable>();
        timetables.add(buildTimetable(new int[]{12, 10, 12, 20, 12, 30}, "Station1", 123, destStation, DayTypes.WEEKDAY));
        timetables.add(buildTimetable(new int[]{12, 12, 12, 22, 12, 32}, "Station2", 123, destStation, DayTypes.WEEKDAY));
        timetables.add(buildTimetable(new int[]{12, 13, 12, 23, 12, 33}, "Station3", 123, destStation, DayTypes.WEEKDAY));
        timetables.add(buildTimetable(new int[]{12, 16, 12, 26, 12, 36}, "Station4", 123, destStation, DayTypes.WEEKDAY));
        Map<DayTypes, List<Transit>> transitsMap = TransitBuilder.buildFromTimetables(timetables);

        assertEquals(1, transitsMap.keySet().size());
        List<Transit> transits = transitsMap.get(DayTypes.WEEKDAY);
        assertNotNull(transits);
        assertEquals(3, transits.size());

        for (Transit transit : transits) {
            validateTransit(transit, 4, 6, destStation);
        }

        Transit transit2 = transits.get(1);
        assertEquals("Station1", transit2.getStops().get(0).getStation());
        assertEquals("Station2", transit2.getStops().get(1).getStation());
        assertEquals("Station3", transit2.getStops().get(2).getStation());
        assertEquals("Station4", transit2.getStops().get(3).getStation());
        assertEquals(TimeUtils.timeValue(12, 20), transit2.getStops().get(0).getTime().getTimeValue());
        assertEquals(TimeUtils.timeValue(12, 22), transit2.getStops().get(1).getTime().getTimeValue());
        assertEquals(TimeUtils.timeValue(12, 23), transit2.getStops().get(2).getTime().getTimeValue());
        assertEquals(TimeUtils.timeValue(12, 26), transit2.getStops().get(3).getTime().getTimeValue());
    }

    private void validateTransit(Transit transit, int stopsCount, int duration, String destStation) {
        assertEquals(stopsCount, transit.getStops().size());
        assertEquals(duration, transit.getDuration());
        assertEquals(destStation, transit.getDestStation());
    }

    private Timetable buildTimetable(int[] times, String station, int lineNo, String destStation, DayTypes dayType) {
        List<Departure> departures = new ArrayList<Departure>();
        for (int i = 0; i < times.length; i+=2) {
            departures.add(new Departure(times[i], times[i + 1]));
        }
        Map<DayTypes, List<Departure>> map = new HashMap<DayTypes, List<Departure>>();
        map.put(dayType, departures);
        return new Timetable(station, lineNo, destStation, map);
    }

}
