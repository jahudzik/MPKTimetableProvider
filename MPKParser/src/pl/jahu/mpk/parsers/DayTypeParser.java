package pl.jahu.mpk.parsers;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.utils.TimeUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-07.
 */
public class DayTypeParser {

    private static final int UNKNOWN = -1;


    public static DayType parse(String literal, boolean nightly, String location) throws TimetableParseException {
        String[] entries = literal.replace(", ", ",").split(",");
        if (entries.length == 1) {
            return parseSingleDayType(literal, nightly, location);
        } else {
            // example: 'Pt/Sob.,Sob./Św.'
            DayType mergedDayType = null;
            for (String entry : entries) {
                DayType singleDayType = parseSingleDayType(entry, nightly, location);
                mergedDayType = merge(mergedDayType, singleDayType, location);
            }
            return mergedDayType;
        }
    }

    private static DayType parseSingleDayType(String literal, boolean nightly, String location) throws TimetableParseException {
        // ex Poniedziałek, Dni powszednie, Święta, Wt.
        Map<Integer, Boolean> daysMap = parseDaysOfWeek(literal);
        if (daysMap != null) {
            return new DayType(daysMap, nightly);
        }

        // ex Pn.-Czw., Sobota-Niedziela
        daysMap = parseDaysOfWeekPeriod(literal);
        if (daysMap != null) {
            return new DayType(daysMap, nightly);
        }

        // ex Sob./Św., Czwartek/Piątek
        daysMap = parseNights(literal);
        if (daysMap != null) {
            return new DayType(daysMap, nightly);
        }

        // ex 01.11.2014, 1-3.05.2015
        DateTime dates[] = parseDates(literal);
        if (dates != null) {
            return (dates.length == 1) ? new DayType(dates[0], nightly) : new DayType(dates[0], dates[1], nightly);
        }

        throw new TimetableParseException("Unsupported day type : '" + literal + "'", location);
    }

    private static DayType merge(DayType dayType1, DayType dayType2, String location) throws TimetableParseException {
        if (dayType1 == null || dayType2 == null) {
            return (dayType1 == null) ? dayType2 : dayType1;
        }
        if (dayType1.getDaysOfWeek() == null || dayType2.getDaysOfWeek() == null) {
            throw new TimetableParseException("Cannot merge day types with dates: " + dayType1 + " & " + dayType2, location);
        }
        Map<Integer, Boolean> daysMap = new HashMap<>();
        for (Integer day : dayType1.getDaysOfWeek().keySet()) {
            daysMap.put(day, dayType1.getDaysOfWeek().get(day) || dayType2.getDaysOfWeek().get(day));
        }
        return new DayType(daysMap, dayType1.isNightly() && dayType2.isNightly());
    }

    private static Map<Integer, Boolean> parseDaysOfWeek(String literal) {
        switch (literal) {
            case "Dzień powszedni":
            case "Dni powszednie":
                return getDaysOfWeekMap(DateTimeConstants.MONDAY, DateTimeConstants.FRIDAY);
            case "Wszystkie dni tygodnia":
                return getDaysOfWeekMap(DateTimeConstants.MONDAY, DateTimeConstants.SUNDAY);
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

    /**
     * For nightly 'A/B' (ex Sob./Św.) it should return map with just first day set for true
     */
    private static Map<Integer, Boolean> parseNights(String literal) {
        String[] values = literal.split("\\/");
        if (values.length < 2) {
            return null;
        }
        return parseDaysOfWeek(values[0]);
    }

    private static DateTime[] parseDates(String literal) {
        String[] values = literal.split("\\.");
        if (values.length < 2) {
            return null;
        }
        int[] days = parseDays(values[0]);
        int month = parseInt(values[1]);
        int year = (values.length > 2) ? parseInt(values[2]) : LocalDate.now().getYear();

        if (days != null && month != UNKNOWN && year != UNKNOWN) {
            switch (days.length) {
                case 1:
                    return new DateTime[]{TimeUtils.buildDate(days[0], month, year)};
                case 2:
                    return new DateTime[]{TimeUtils.buildDate(days[0], month, year), TimeUtils.buildDate(days[1], month, year)};
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
                return DateTimeConstants.SATURDAY;
            case "Święta":
            case "Św.":
            case "Niedziela":
            case "Niedziele":
            case "Nie":
            case "Nie.":
            case "Niedz":
            case "Niedz.":
                return DateTimeConstants.SUNDAY;
            case "Poniedziałek":
            case "Pn":
            case "Pon":
            case "Pon.":
                return DateTimeConstants.MONDAY;
            case "Wtorek":
            case "Wt":
            case "Wt.":
            case "Wto":
            case "Wto.":
                return DateTimeConstants.TUESDAY;
            case "Środa":
            case "Śr":
            case "Śr.":
            case "Śro":
            case "Śro.":
                return DateTimeConstants.WEDNESDAY;
            case "Czwartek":
            case "Czw":
            case "Czw.":
                return DateTimeConstants.THURSDAY;
            case "Piątek":
            case "Pi":
            case "Pi.":
            case "Pt":
                return DateTimeConstants.FRIDAY;
        }
        return UNKNOWN;
    }

    private static Map<Integer, Boolean> getDaysOfWeekMap(int firstDayIndex, int lastDayIndex) {
        if (firstDayIndex == UNKNOWN || lastDayIndex == UNKNOWN) {
            return null;
        }
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(DateTimeConstants.MONDAY, dayInRange(DateTimeConstants.MONDAY, firstDayIndex, lastDayIndex));
        map.put(DateTimeConstants.TUESDAY, dayInRange(DateTimeConstants.TUESDAY, firstDayIndex, lastDayIndex));
        map.put(DateTimeConstants.WEDNESDAY, dayInRange(DateTimeConstants.WEDNESDAY, firstDayIndex, lastDayIndex));
        map.put(DateTimeConstants.THURSDAY, dayInRange(DateTimeConstants.THURSDAY, firstDayIndex, lastDayIndex));
        map.put(DateTimeConstants.FRIDAY, dayInRange(DateTimeConstants.FRIDAY, firstDayIndex, lastDayIndex));
        map.put(DateTimeConstants.SATURDAY, dayInRange(DateTimeConstants.SATURDAY, firstDayIndex, lastDayIndex));
        map.put(DateTimeConstants.SUNDAY, dayInRange(DateTimeConstants.SUNDAY, firstDayIndex, lastDayIndex));
        return map;
    }

    private static Map<Integer, Boolean> getWeekendMap() {
        Map<Integer, Boolean> map = new HashMap<>();
        map.put(DateTimeConstants.MONDAY, false);
        map.put(DateTimeConstants.TUESDAY, false);
        map.put(DateTimeConstants.WEDNESDAY, false);
        map.put(DateTimeConstants.THURSDAY, false);
        map.put(DateTimeConstants.FRIDAY, false);
        map.put(DateTimeConstants.SATURDAY, true);
        map.put(DateTimeConstants.SUNDAY, true);
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