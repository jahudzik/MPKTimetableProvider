package pl.jahu.mpk.validators.exceptions;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-09-06.
 */
public class UnsupportedLineNumberException extends Exception {

    public UnsupportedLineNumberException(String value) {
        super("'" + value + "'");
    }
}
