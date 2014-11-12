package pl.jahu.mpk.parsers;

import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.utils.TimeUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-07.
 */
public class DayTypeParser {

    private static final int UNKNOWN = -1;


    public static DayType parse(String literal, String location) throws TimetableParseException {

        Map<Integer, Boolean> daysMap = parseDaysOfWeek(literal);
        if (daysMap != null) {
            return new DayType(daysMap, false);
        }

        daysMap = parseDaysOfWeekPeriod(literal);
        if (daysMap != null) {
            return new DayType(daysMap, false);
        }

        daysMap = parseNights(literal);
        if (daysMap != null) {
            return new DayType(daysMap, true);
        }

        Date dates[] = parseDates(literal);
        if (dates != null) {
            return (dates.length == 1) ? new DayType(dates[0]) : new DayType(dates[0], dates[1]);
        }

        throw new TimetableParseException("Unsupported day type : '" + literal + "'", location);
    }

    private static Map<Integer, Boolean> parseDaysOfWeek(String literal) {
        switch (literal) {
            case "Dzień powszedni":
            case "Dni powszednie":
                return getDaysOfWeekMap(Calendar.MONDAY, Calendar.FRIDAY);
            case "Wszystkie dni tygodnia":
                return getDaysOfWeekMap(Calendar.MONDAY, Calendar.SUNDAY);
            case "Weekend":
                return getWeekendMap();
            default:
                // single day of week
                int dayIndex = getDayOfWeekFromString(literal);
                return getDaysOfWeekMap(dayIndex, dayIndex);
        }
    }

    private static Map<Integer, Boolean> parseDaysOfWeekPeriod(String literal) {
        return parseTwoDaysCombined(literal, "-");
    }

    private static Map<Integer, Boolean> parseNights(String literal) {
        return parseTwoDaysCombined(literal, "/");
    }

    private static Date[] parseDates(String literal) {
        String[] values = literal.split("\\.");
        if (values.length < 2) {
            return null;
        }
        int[] days = parseDays(values[0]);
        int month = parseInt(values[1]);
        int year = (values.length > 2) ? parseInt(values[2]) : Calendar.getInstance().get(Calendar.YEAR);

        if (days != null && month != UNKNOWN && year != UNKNOWN) {
            switch (days.length) {
                case 1:
                    return new Date[]{TimeUtils.buildDate(days[0], month, year)};
                case 2:
                    return new Date[]{TimeUtils.buildDate(days[0], month, year), TimeUtils.buildDate(days[1], month, year)};
            }
        }
        return null;
    }

    private static int[] parseDays(String daysString) {
        if (daysString.contains("-")) {
            String[] twoDays = daysString.split("-");
            int firstDay = parseInt(twoDays[0]);
            int secondDay = parseInt(twoDays[1]);
            return (firstDay != UNKNOWN && secondDay != UNKNOWN) ? new int[]{firstDay, secondDay} : null;
        } else {
            int day = parseInt(daysString);
            return (day != UNKNOWN) ? new int[]{day} : null;
        }
    }

    private static int parseInt(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            return UNKNOWN;
        }
    }

    private static Map<Integer, Boolean> parseTwoDaysCombined(String literal, String separator) {
        if (literal.contains(separator)) {
            int firstDayIndex = getDayOfWeekFromString(literal.substring(0, literal.indexOf(separator)));
            int lastDayIndex = getDayOfWeekFromString(literal.substring(literal.indexOf(separator) + separator.length()));
            return (firstDayIndex != UNKNOWN && lastDayIndex != UNKNOWN) ? getDaysOfWeekMap(firstDayIndex, lastDayIndex) : null;
        }
        return null;
    }

    private static int getDayOfWeekFromString(String dayName) {
        switch (dayName) {
            case "Sobota":
            case "Soboty":
            case "Sb":
            case "Sob":
            case "Sob.":
                return Calendar.SATURDAY;
            case "Święta":
            case "Św.":
            case "Niedziela":
            case "Niedziele":
            case "Nie":
            case "Nie.":
            case "Niedz":
            case "Niedz.":
                return Calendar.SUNDAY;
            case "Poniedziałek":
            case "Pn":
            case "Pon":
            case "Pon.":
                return Calendar.MONDAY;
            case "Wtorek":
            case "Wt":
            case "Wt.":
            case "Wto":
            case "Wto.":
                return Calendar.TUESDAY;
            case "Środa":
            case "Śr":
            case "Śr.":
            case "Śro":
            case "Śro.":
                return Calendar.WEDNESDAY;
            case "Czwartek":
            case "Czw":
            case "Czw.":
                return Calendar.THURSDAY;
            case "Piątek":
            case "Pi":
            case "Pi.":
            case "Pt":
                return Calendar.FRIDAY;
        }
        return UNKNOWN;
    }

    private static Map<Integer, Boolean> getDaysOfWeekMap(int firstDayIndex, int lastDayIndex) {
        if (firstDayIndex == UNKNOWN || lastDayIndex == UNKNOWN) {
            return null;
        }
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(Calendar.MONDAY, dayInRange(Calendar.MONDAY, firstDayIndex, lastDayIndex));
        map.put(Calendar.TUESDAY, dayInRange(Calendar.TUESDAY, firstDayIndex, lastDayIndex));
        map.put(Calendar.WEDNESDAY, dayInRange(Calendar.WEDNESDAY, firstDayIndex, lastDayIndex));
        map.put(Calendar.THURSDAY, dayInRange(Calendar.THURSDAY, firstDayIndex, lastDayIndex));
        map.put(Calendar.FRIDAY, dayInRange(Calendar.FRIDAY, firstDayIndex, lastDayIndex));
        map.put(Calendar.SATURDAY, dayInRange(Calendar.SATURDAY, firstDayIndex, lastDayIndex));
        map.put(Calendar.SUNDAY, dayInRange(Calendar.SUNDAY, firstDayIndex, lastDayIndex));
        return map;
    }

    private static Map<Integer, Boolean> getWeekendMap() {
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(Calendar.MONDAY, false);
        map.put(Calendar.TUESDAY, false);
        map.put(Calendar.WEDNESDAY, false);
        map.put(Calendar.THURSDAY, false);
        map.put(Calendar.FRIDAY, false);
        map.put(Calendar.SATURDAY, true);
        map.put(Calendar.SUNDAY, true);
        return map;
    }


    private static boolean dayInRange(int day, int firstDayIndex, int lastDayIndex) {
        if (firstDayIndex <= lastDayIndex) {
            return (day >= firstDayIndex && day <= lastDayIndex);
        } else {
            return (day >= firstDayIndex || day <= lastDayIndex);
        }
    }


}