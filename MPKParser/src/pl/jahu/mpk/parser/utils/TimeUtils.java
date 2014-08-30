package pl.jahu.mpk.parser.utils;

import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.exceptions.UnsupportedDayTypesConfigurationException;

import java.util.Calendar;

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtils {

    public static boolean validateDayTypeForToday(DayTypes dayType) throws UnsupportedDayTypesConfigurationException {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return validateDayType(dayType, dayOfWeek);
    }

    public static boolean validateDayType(DayTypes dayType, int dayOfWeek) throws UnsupportedDayTypesConfigurationException {
        if (dayType == null) {
            throw new UnsupportedDayTypesConfigurationException();
        }
        switch (dayType) {
            case WEEKDAY:
                return (dayOfWeek != Calendar.SUNDAY && dayOfWeek != Calendar.SATURDAY);
            case SATURDAY:
                return (dayOfWeek == Calendar.SATURDAY);
            case SUNDAY:
                return (dayOfWeek == Calendar.SUNDAY);
            case EVERYDAY:
                return true;
            case MONDAY_TO_THURSDAY:
                return (dayOfWeek == Calendar.MONDAY || dayOfWeek == Calendar.THURSDAY || dayOfWeek == Calendar.WEDNESDAY || dayOfWeek == Calendar.THURSDAY);
            case WEEKEND_NIGHTS:
                return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
            default:
                throw new UnsupportedDayTypesConfigurationException(dayType.name());
        }
    }

}
