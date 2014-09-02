package pl.jahu.mpk.validators.exceptions;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-02.
 */
public class IncorrectTimeDifferenceBetweenStopsException extends TransitValidationException {

    public IncorrectTimeDifferenceBetweenStopsException(String stations, String transit1, String transit2) {
        super("Stations: " + stations + ", transit1: '" + transit1 + "', transit2: '" + transit2 + "'");
    }

    public IncorrectTimeDifferenceBetweenStopsException(String transit) {
        super("Transit: " + transit);
    }

}
