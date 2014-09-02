package pl.jahu.mpk.validators.exceptions;

import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.utils.Time;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-02.
 */
public class UnhandledTimetableDepartureException extends TransitValidationException {

    public UnhandledTimetableDepartureException(int line, String station, String destStation, DayTypes dayType, Time departureTime) {
        super("Line: " + line + ", Station: " + station + ", Direction: " + destStation + ", Day type: " + dayType + ", Departure: " + departureTime);
    }

}
