package pl.jahu.mpk.parsers.exceptions;

/**
 * Created by jahudzik on 2014-07-14.
 */
public class TimetableParseException extends Exception {

    public TimetableParseException(String reason, String location) {
        super(reason + " [location: " + location + "]");
    }

}
