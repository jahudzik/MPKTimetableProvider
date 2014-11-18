package pl.jahu.mpk.utils;

import org.joda.time.DateTime;

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtils {

    /**
     * Planned to be used in comparing transits times
     */
    public static boolean moreLessEqual(Time time1, Time time2) {
        return Math.abs(time1.compareDaytimeTo(time2)) < 3;
    }

    public static int timeValue(int hour, int min) {
        return hour * 60 + min;
    }

    public static DateTime buildDate(int day, int month, int year) {
        return buildDate(12, 0, day, month, year);
    }

    public static DateTime buildDate(int hour, int min, int day, int month, int year) {
        return new DateTime(year, month, day, hour, min);
    }

    public static int previousDay(int day) {
        return (day != 1) ? day - 1 : 7;
    }

}
