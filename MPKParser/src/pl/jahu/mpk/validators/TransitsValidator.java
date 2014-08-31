package pl.jahu.mpk.validators;

import pl.jahu.mpk.entities.Transit;
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


    public static void validate(List<Transit> transits) throws TransitValidationException{
        validateTransitDurations(transits);
    }

    private static void validateTransitDurations(List<Transit> transits) throws IncorrectTransitDurationException {
        Map<String, Transit> durations = new HashMap<String, Transit>();
        for (Transit transit : transits) {
            String key = transit.getStops().get(0).getStation() + "->" + transit.getDestStation();
            int duration = transit.getLastStopTime().getTimeValue() - transit.getStops().get(0).getTime().getTimeValue();
            if (durations.containsKey(key)) {
                Transit prevTransit = durations.get(key);
                int prevDuration = prevTransit.getDuration();
                if (!moreLessEqual(duration, prevDuration)) {
                    throw new IncorrectTransitDurationException("Previous transit (" + prevDuration + " mins): " + prevTransit.getDirections() + ", actual transit (" + transit.getDuration() + "mins) : " + transit.getDirections());
                }
            } else {
                durations.put(key, transit);
            }
        }

    }


    private static boolean moreLessEqual(int value1, int value2) {
        return Math.abs(value1 - value2) < 10;
    }


}
