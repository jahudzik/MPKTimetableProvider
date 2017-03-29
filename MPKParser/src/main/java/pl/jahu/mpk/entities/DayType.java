package pl.jahu.mpk.entities;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import pl.jahu.mpk.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-07.
 */
public final class DayType {

    private static final int DAY_START = 3;
    private static final int DAY_END = 1;
    private static final int NIGHT_START = 23;
    private static final int NIGHT_END = 6;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd.MM.yyyy");

    private final DateTime startDate;
    private final DateTime endDate;
    private final Map<Integer, Boolean> daysOfWeek;
    private final boolean nightly;

    public DayType(Map<Integer, Boolean> daysOfWeek, boolean nightly) {
        this.startDate = null;
        this.endDate = null;
        this.daysOfWeek = daysOfWeek;
        this.nightly = nightly;
    }

    public DayType(DateTime date) {
        this(date, false);
    }

    public DayType(DateTime date, boolean nightly) {
        this.startDate = date;
        this.endDate = date;
        this.daysOfWeek = null;
        this.nightly = nightly;
    }

    public DayType(DateTime startDate, DateTime endDate) {
        this(startDate, endDate, false);
    }

    public DayType(DateTime startDate, DateTime endDate, boolean nightly) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysOfWeek = null;
        this.nightly = nightly;
    }

    public static DayType getInstance(int[] expectedDaysOfWeek, boolean nightly) {
        boolean[] expectedValues = new boolean[8];
        for (int day : expectedDaysOfWeek) {
            expectedValues[day] = true;
        }
        Map<Integer, Boolean> daysOfWeek = new HashMap<>();
        daysOfWeek.put(DateTimeConstants.MONDAY, expectedValues[DateTimeConstants.MONDAY]);
        daysOfWeek.put(DateTimeConstants.TUESDAY, expectedValues[DateTimeConstants.TUESDAY]);
        daysOfWeek.put(DateTimeConstants.WEDNESDAY, expectedValues[DateTimeConstants.WEDNESDAY]);
        daysOfWeek.put(DateTimeConstants.THURSDAY, expectedValues[DateTimeConstants.THURSDAY]);
        daysOfWeek.put(DateTimeConstants.FRIDAY, expectedValues[DateTimeConstants.FRIDAY]);
        daysOfWeek.put(DateTimeConstants.SATURDAY, expectedValues[DateTimeConstants.SATURDAY]);
        daysOfWeek.put(DateTimeConstants.SUNDAY, expectedValues[DateTimeConstants.SUNDAY]);
        return new DayType(daysOfWeek, nightly);
    }

    public boolean matches(DateTime date) {
        int hour = date.getHourOfDay();
        int min = date.getMinuteOfHour();
        int dayOfWeek = date.getDayOfWeek();
        if (daysOfWeek != null) {
            if (nightly) {
                if (hour >= NIGHT_START) {
                    // any time between 22:00 and 00:00
                    return daysOfWeek.get(dayOfWeek);
                }
                if (hour <= NIGHT_END) {
                    // any time between 00:00 and 06:59
                    return daysOfWeek.get(TimeUtils.previousDay(dayOfWeek));
                }
                throw new IllegalArgumentException("Incorrect time (" + hour + ":" + min + ") for nightly day type");
            } else {
                if (hour >= DAY_START) {
                    // any time between 03:00 and 00:00
                    return daysOfWeek.get(dayOfWeek);
                }
                if (hour <= DAY_END) {
                    // any time between 00:00 and 01:59
                    return daysOfWeek.get(TimeUtils.previousDay(dayOfWeek));
                }
                throw new IllegalArgumentException("Incorrect time (" + hour + ":" + min + ") for daily day type");
            }
        } else {
            return (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0);
        }
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public Map<Integer, Boolean> getDaysOfWeek() {
        return daysOfWeek;
    }

    public boolean isNightly() {
        return nightly;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DayType)) {
            return false;
        }
        DayType otherDayType = (DayType) obj;
        if (otherDayType.startDate != null && otherDayType.endDate != null) {
            return startDate != null && endDate != null && startDate.equals(otherDayType.startDate) && endDate.equals(otherDayType.endDate);
        } else {
            // otherDayType.daysOfWeek != null
            if (daysOfWeek == null) {
                return false;
            }
            for (Integer dayOfWeek : daysOfWeek.keySet()) {
                if (daysOfWeek.get(dayOfWeek) != otherDayType.daysOfWeek.get(dayOfWeek)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + ((startDate != null) ? startDate.hashCode() : 0);
        result = 31 * result + ((endDate != null) ? endDate.hashCode() : 0);
        result = 31 * result + (nightly ? 1 : 0);
        if (daysOfWeek != null) {
            for (int day = 1; day <= 7; day++) {
                result = 31 * result + (daysOfWeek.get(day) ? 1 : 0);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (startDate != null && endDate != null) {
            sb.append(DATE_FORMATTER.print(startDate)).append("-").append(DATE_FORMATTER.print(endDate));
        } else {
            sb.append(nightly ? "nightly:" : "daily:");
            if (daysOfWeek.get(DateTimeConstants.MONDAY)) {
                sb.append("Mon|");
            }
            if (daysOfWeek.get(DateTimeConstants.TUESDAY)) {
                sb.append("Tue|");
            }
            if (daysOfWeek.get(DateTimeConstants.WEDNESDAY)) {
                sb.append("Wed|");
            }
            if (daysOfWeek.get(DateTimeConstants.THURSDAY)) {
                sb.append("Thu|");
            }
            if (daysOfWeek.get(DateTimeConstants.FRIDAY)) {
                sb.append("Fri|");
            }
            if (daysOfWeek.get(DateTimeConstants.SATURDAY)) {
                sb.append("Sat|");
            }
            if (daysOfWeek.get(DateTimeConstants.SUNDAY)) {
                sb.append("Sun|");
            }
            sb.deleteCharAt(sb.lastIndexOf("|"));
        }
        sb.append("]");
        return sb.toString();
    }

}
