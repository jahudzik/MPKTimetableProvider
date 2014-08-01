package pl.jahu.mpk.entities;

import pl.jahu.mpk.enums.DayTypes;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class Departure {

    private Station station;
    private Transit transit;
    private int seq;
    private int hour;
    private int min;
    private String extraInfo;

    public Departure(Station station, int hour, int min) {
        this.station = station;
        this.hour = hour;
        this.min = min;
    }

    public Departure(Station station, int hour, int min, String extraInfo) {
        this(station, hour, min);
        this.extraInfo = extraInfo;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Transit getTransit() {
        return transit;
    }

    public void setTransit(Transit transit) {
        this.transit = transit;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
