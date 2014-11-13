package pl.jahu.mpk.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-07.
 */
public class DayType {

    private final Date startDate;

    private final Date endDate;

    private final Map<Integer, Boolean> daysOfWeek;

    // TODO handle night day types
    private final boolean nightly;

    public DayType(Map<Integer, Boolean> daysOfWeek) {
        this(daysOfWeek, false);
    }

    public DayType(Map<Integer, Boolean> daysOfWeek, boolean nightly) {
        this.startDate = null;
        this.endDate = null;
        this.daysOfWeek = daysOfWeek;
        this.nightly = nightly;
    }

    public DayType(Date date) {
        this(date, false);
    }

    public DayType(Date date, boolean nightly) {
        this.startDate = date;
        this.endDate = date;
        this.daysOfWeek = null;
        this.nightly = nightly;
    }

    public DayType(Date startDate, Date endDate) {
        this(startDate, endDate, false);
    }

    public DayType(Date startDate, Date endDate, boolean nightly) {
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
        daysOfWeek.put(Calendar.MONDAY, expectedValues[Calendar.MONDAY]);
        daysOfWeek.put(Calendar.TUESDAY, expectedValues[Calendar.TUESDAY]);
        daysOfWeek.put(Calendar.WEDNESDAY, expectedValues[Calendar.WEDNESDAY]);
        daysOfWeek.put(Calendar.THURSDAY, expectedValues[Calendar.THURSDAY]);
        daysOfWeek.put(Calendar.FRIDAY, expectedValues[Calendar.FRIDAY]);
        daysOfWeek.put(Calendar.SATURDAY, expectedValues[Calendar.SATURDAY]);
        daysOfWeek.put(Calendar.SUNDAY, expectedValues[Calendar.SUNDAY]);
        return new DayType(daysOfWeek, nightly);
    }

    public boolean matches(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (daysOfWeek != null) {
            return daysOfWeek.get(cal.get(Calendar.DAY_OF_WEEK));
        } else {
            return (date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Map<Integer, Boolean> getDaysOfWeek() {
        return daysOfWeek;
    }

    public boolean isNightly() {
        return nightly;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (startDate != null && endDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            sb.append(cal.get(Calendar.DAY_OF_MONTH)).append(".");
            sb.append(cal.get(Calendar.MONTH) + 1).append(".");
            sb.append(cal.get(Calendar.YEAR)).append("-");
            cal.setTime(endDate);
            sb.append(cal.get(Calendar.DAY_OF_MONTH)).append(".");
            sb.append(cal.get(Calendar.MONTH) + 1).append(".");
            sb.append(cal.get(Calendar.YEAR));
        } else {
            sb.append(nightly ? "nightly:" : "daily:");
            if (daysOfWeek.get(Calendar.MONDAY)) {
                sb.append("Mon|");
            }
            if (daysOfWeek.get(Calendar.TUESDAY)) {
                sb.append("Tue|");
            }
            if (daysOfWeek.get(Calendar.WEDNESDAY)) {
                sb.append("Wed|");
            }
            if (daysOfWeek.get(Calendar.THURSDAY)) {
                sb.append("Thu|");
            }
            if (daysOfWeek.get(Calendar.FRIDAY)) {
                sb.append("Fri|");
            }
            if (daysOfWeek.get(Calendar.SATURDAY)) {
                sb.append("Sat|");
            }
            if (daysOfWeek.get(Calendar.SUNDAY)) {
                sb.append("Sun|");
            }
            sb.deleteCharAt(sb.lastIndexOf("|"));
        }
        sb.append("]");
        return sb.toString();
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

}
