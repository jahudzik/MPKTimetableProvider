package pl.jahu.mpk.utils;

import java.util.Calendar;
import java.util.Date;

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

    public static Date buildDate(int day, int month, int year) {
        return buildDate(12, 0, day, month, year);
    }

    public static Date buildDate(int hour, int min, int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, month - 1, day, hour, min);
        return cal.getTime();
    }

    public static int previousDay(int day) {
        return (day != 1) ? day - 1 : 7;
    }

}
