package pl.jahu.mpk.parsers.data;

import org.jsoup.nodes.Document;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-06.
 */
public class ParsableData {

    private Document document;

    private String location;

    public ParsableData(Document document, String location) {
        this.document = document;
        this.location = location;
    }

    public Document getDocument() {
        return document;
    }

    public String getLocation() {
        return location;
    }

}
