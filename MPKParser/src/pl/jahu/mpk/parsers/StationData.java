package pl.jahu.mpk.parsers;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class StationData {

    private String name;
    private String address;

    public StationData(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
