package pl.jahu.mpk.validators.exceptions;

import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.utils.Time;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-02.
 */
public class UnhandledTimetableDepartureException extends TransitValidationException {

    public UnhandledTimetableDepartureException(String line, String station, String destStation, DayType dayType, Time departureTime) {
        super("Line: " + line + ", Station: " + station + ", Direction: " + destStation + ", Day type: " + dayType + ", Departure: " + departureTime);
    }

}
