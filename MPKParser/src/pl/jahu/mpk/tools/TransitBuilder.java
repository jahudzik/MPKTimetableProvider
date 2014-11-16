package pl.jahu.mpk.tools;

import pl.jahu.mpk.entities.*;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.utils.Time;
import pl.jahu.mpk.validators.TransitsValidator;
import pl.jahu.mpk.validators.exceptions.TransitValidationException;
import pl.jahu.mpk.validators.exceptions.UnhandledTimetableDepartureException;

import javax.inject.Inject;
import java.util.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-01.
 */
public class TransitBuilder {

    public static final int MAX_TIME_DIFF_BETWEEN_STOPS = 5;

    @Inject
    static TimetableProvider timetableProvider;

    /**
     * Parses timetables of specified line in specified direction and builds list of transits.
     */
    public static List<Transit> parseAndBuild(Date date, LineNumber lineNo, int direction) throws ParsableDataNotFoundException, TimetableParseException, TransitValidationException {
        List<Timetable> timetables = new ArrayList<>();
        timetableProvider.getLineRoute(lineNo, direction);
        List<StationData> stations = timetableProvider.getLineRoute(lineNo, direction);
        for (StationData station : stations) {
            timetables.add(timetableProvider.getTimetable(date, lineNo, station.getSequenceNumber()));
        }
        return buildFromTimetables(timetables);
    }

    public static void printTransitsList(DayType dayType, List<Transit> transitList) {
        Map<DayType, List<Transit>> transitsMap = new HashMap<>();
        transitsMap.put(dayType, transitList);
        printTransitsMap(transitsMap);
    }

    public static void printTransitsMap(Map<DayType, List<Transit>> transitsMap) {
        for (DayType dayType : transitsMap.keySet()) {
            System.out.println("* " + dayType + " :");
            for (Transit transit : transitsMap.get(dayType)) {
                System.out.println(transit);
            }
            System.out.println();
        }
    }


    /**
     * Converts list of timetables (all in common direction) into list of transits grouped by day types.
     */
    public static List<Transit> buildFromTimetables(List<Timetable> timetables) throws TransitValidationException {
        List<Transit> transits = new ArrayList<>();

        for (Timetable timetable : timetables) {
            List<Departure> departures = timetable.getDepartures();
            if (transits.size() == 0) {
                // first station on the route, create new transits
                for (Departure departure : departures) {
                    Transit transit = new Transit(timetable.getLine());
                    transit.addStop(new TransitStop(departure.getTime(), timetable.getStation()));
                    transits.add(transit);
                }
            } else {
                int transitId = 0;
                for (Departure departure : departures) {
                    if (transitId == -1) {
                        // all transits has been matched to the departures, but there's more unhandled departures
                        throw new UnhandledTimetableDepartureException(timetable.getLine().toString(), timetable.getStation(), timetable.getDestStation(), timetable.getDayType(), departure.getTime());
                    }
                    Time lastStopTime = transits.get(transitId).getLastStopTime();
                    if (lastStopTime.compareDaytimeTo(departure.getTime()) > 0) {
                        // if this departure is earlier than the first transit so far, begin new transit from this station
                        Transit transit = new Transit(timetable.getLine());
                        transit.addStop(new TransitStop(departure.getTime(), timetable.getStation()));
                        transits.add(transitId, transit);
                    } else {
                        while (transitId != -1 && departure.getTime().compareDaytimeTo(lastStopTime) > MAX_TIME_DIFF_BETWEEN_STOPS) {
                            // there's too big difference in departures times - finish transit on this station and consider next one
                            transits.get(transitId).setDestStation(timetable.getStation());
                            transitId = increaseTransitId(transitId, transits);
                            if (transitId != -1) {
                                lastStopTime = transits.get(transitId).getLastStopTime();
                            }
                        }
                        if (transitId == -1) {
                            // all transits has been matched to the departures, so this departure will be unhandled
                            throw new UnhandledTimetableDepartureException(timetable.getLine().toString(), timetable.getStation(), timetable.getDestStation(), timetable.getDayType(), departure.getTime());
                        }

                        transits.get(transitId).addStop(new TransitStop(departure.getTime(), timetable.getStation()));
                    }
                    transitId = increaseTransitId(transitId, transits);
                }

                if (transitId < transits.size() && transitId != -1) {
                    // there are missing departures for some transits - assume these transits end here and set them destination station
                    for (int j = transitId; j < transits.size(); j++) {
                        if (transits.get(j).getDestStation() == null) {
                            transits.get(j).setDestStation(timetable.getStation());
                        }
                    }
                }
            }
        }

        String destStation = timetables.get(0).getDestStation();
        for (Transit transit : transits) {
            if (transit.getDestStation() == null) {
                transit.setDestStation(destStation);
            }
        }

        TransitsValidator.validate(transits);
        return transits;
    }

    /**
     * Returns index of a next not-finished transit from the list. If there're no transits left, returns -1.
     */
    private static int increaseTransitId(int actTransitId, List<Transit> transits) {
        actTransitId++;
        while (actTransitId < transits.size() && transits.get(actTransitId).getDestStation() != null) {
            actTransitId++;
        }
        return (actTransitId < transits.size()) ? actTransitId : -1;
    }
}