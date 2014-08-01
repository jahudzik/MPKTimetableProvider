package pl.jahu.mpk.entities;

import pl.jahu.mpk.enums.DayTypes;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class Transit {

    private Line line;
    private DayTypes day;

    public Transit(Line line, DayTypes day) {
        this.line = line;
        this.day = day;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public DayTypes getDay() {
        return day;
    }

    public void setDay(DayTypes day) {
        this.day = day;
    }
}
