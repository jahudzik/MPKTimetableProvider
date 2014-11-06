package pl.jahu.mpk.parsers.data;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class StationData {

    private String name;
    private String urlLocation;

    public StationData(String name, String address) {
        this.name = name;
        this.urlLocation = address;
    }

    public String getName() {
        return name;
    }

    public String getUrlLocation() {
        return urlLocation;
    }

}
