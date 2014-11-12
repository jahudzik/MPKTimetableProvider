package pl.jahu.mpk.entities;

import java.util.Calendar;
import java.util.Date;
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


    public DayType(Map<Integer, Boolean> daysOfWeek, boolean nightly) {
        this.startDate = null;
        this.endDate = null;
        this.daysOfWeek = daysOfWeek;
        this.nightly = nightly;
    }

    public DayType(Date date) {
        this.startDate = date;
        this.endDate = date;
        this.daysOfWeek = null;
        this.nightly = false;
    }

    public DayType(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.daysOfWeek = null;
        this.nightly = false;
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
}
