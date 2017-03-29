package pl.jahu.mpk.validators;

import pl.jahu.mpk.entities.Transit;
import pl.jahu.mpk.entities.TransitStop;
import pl.jahu.mpk.validators.exceptions.IncorrectTimeDifferenceBetweenStopsException;
import pl.jahu.mpk.validators.exceptions.IncorrectTransitDurationException;
import pl.jahu.mpk.validators.exceptions.TransitValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-31.
 */
public class TransitsValidator {

    private static final int TRANSITS_DURATION_MARGIN_OF_ERROR = 6;
    private static final int DIFF_BETWEEN_STOPS_MARGIN_OF_ERROR = 3;

    public static void validate(List<Transit> transits) throws TransitValidationException{
        validateTransitDurations(transits);
        validateDiffBetweenStops(transits);
    }

    private static void validateTransitDurations(List<Transit> transits) throws IncorrectTransitDurationException {
        Map<String, Transit> durations = new HashMap<>();
        for (Transit transit : transits) {
            String key = transit.getStops().get(0).getStation() + "->" + transit.getDestStation();
            if (durations.containsKey(key)) {
                Transit prevTransit = durations.get(key);
                int prevDuration = prevTransit.getDuration();
                if (!moreLessEqual(transit.getDuration(), prevDuration, TRANSITS_DURATION_MARGIN_OF_ERROR)) {
                    throw new IncorrectTransitDurationException("Previous transit (" + prevDuration + " mins): " + prevTransit.getDirections() + ", actual transit (" + transit.getDuration() + " mins) : " + transit.getDirections());
                }
            } else {
                durations.put(key, transit);
            }
        }
    }

    private static void validateDiffBetweenStops(List<Transit> transits) throws IncorrectTimeDifferenceBetweenStopsException {
        Map<String, DiffBetweenStopsInfo> durations = new HashMap<>();
        for (Transit transit : transits) {
            TransitStop prevStop = null;
            for (TransitStop stop : transit.getStops()) {
                if (prevStop != null) {

                    // validate if this stop's time is bigger than previous stop
                    if (stop.getTime().compareDaytimeTo(prevStop.getTime()) <= 0) {
                        throw new IncorrectTimeDifferenceBetweenStopsException(transit.toString());
                    }

                    // validate time difference between these two stops compared to previous transits
                    String key = prevStop.getStation() + "->" + stop.getStation();
                    int diff = stop.getTime().compareDaytimeTo(prevStop.getTime());
                    if (durations.containsKey(key)) {
                        DiffBetweenStopsInfo info = durations.get(key);
                        if (!moreLessEqual(info.diff, diff, DIFF_BETWEEN_STOPS_MARGIN_OF_ERROR)) {
                            throw new IncorrectTimeDifferenceBetweenStopsException(key, info.transit.toString(), transit.toString());
                        }
                    } else {
                        durations.put(key, new DiffBetweenStopsInfo(transit, diff));
                    }
                }
                prevStop = stop;
            }
        }
    }

    private static boolean moreLessEqual(int value1, int value2, int marginOfError) {
        return Math.abs(value1 - value2) < marginOfError;
    }

    private static class DiffBetweenStopsInfo {

        Transit transit;
        int diff;

        private DiffBetweenStopsInfo(Transit transit, int diff) {
            this.transit = transit;
            this.diff = diff;
        }
    }

}
