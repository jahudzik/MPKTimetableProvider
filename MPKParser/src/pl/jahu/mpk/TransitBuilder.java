package pl.jahu.mpk;

import pl.jahu.mpk.entities.*;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parsers.StationData;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
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
    public static Map<DayTypes, List<Transit>> parseAndBuild(LineNumber lineNo, int direction) throws TimetableNotFoundException, TimetableParseException, TransitValidationException {
        List<Timetable> timetables = new ArrayList<Timetable>();
        timetableProvider.getLineRoute(lineNo, direction);
        List<StationData> stations = timetableProvider.getLineRoute(lineNo, direction);
        for (StationData station : stations) {
            timetables.add(timetableProvider.getTimetable(lineNo, station.getUrlLocation()));
        }
        Map<DayTypes, List<Transit>> transitsMap = buildFromTimetables(timetables);

        printTransitsMap(transitsMap);
        return transitsMap;
    }

    public static void printTransitsMap(Map<DayTypes, List<Transit>> transitsMap) {
        for (DayTypes dayType : transitsMap.keySet()) {
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
    public static Map<DayTypes, List<Transit>> buildFromTimetables(List<Timetable> timetables) throws TransitValidationException {
        Map<DayTypes, List<Transit>> resultMap = new HashMap<DayTypes, List<Transit>>();
        if (timetables != null) {
            Set<DayTypes> dayTypes = timetables.get(0).getDepartures().keySet();
            for (DayTypes dayType : dayTypes) {
                resultMap.put(dayType, new ArrayList<Transit>());
            }
            for (Timetable timetable : timetables) {
                for (DayTypes dayType : dayTypes) {
                    List<Transit> transits = resultMap.get(dayType);
                    List<Departure> departures = timetable.getDepartures().get(dayType);
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
                                throw new UnhandledTimetableDepartureException(timetable.getLine().toString(), timetable.getStation(), timetable.getDestStation(), dayType, departure.getTime());
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
                                    throw new UnhandledTimetableDepartureException(timetable.getLine().toString(), timetable.getStation(), timetable.getDestStation(), dayType, departure.getTime());
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
                    resultMap.put(dayType, transits);
                }
            }

            for (DayTypes dayType : dayTypes) {
                for (Transit transit : resultMap.get(dayType)) {
                    if (transit.getDestStation() == null) {
                        transit.setDestStation(timetables.get(0).getDestStation());
                    }
                }
            }
        }

        TransitsValidator.validate(resultMap);
        return resultMap;
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
