package pl.jahu.mpk.entities;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class Line {

    private Integer number;
    private LineType type;

    public Line(Integer number, LineType type) {
        this.number = number;
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LineType getType() {
        return type;
    }

    public void setType(LineType type) {
        this.type = type;
    }
}
