package pl.jahu.mpk.parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;

import java.util.ArrayList;
import java.util.List;

// TODO Handle irregular routes (eg 183, 225)
// TODO Handle lines with one direction (405, 451) if required

/**
 * Created by hudzj on 8/1/2014.
 */
public class LineRouteParser {

    /**
     * Parses chosen line route page
     * @return list of stations on the route without destination station
     */
    public List<StationData> parse(Document document) throws LineRouteParseException {
        List<StationData> list = new ArrayList<StationData>();
        Elements stations = document.getElementsByAttributeValue("target", "R");
        for (Element station : stations) {
            String stationName = station.text();
            if (stationName.equals("")) {
                throw new LineRouteParseException("No station name found");
            }
            String url = station.attributes().get("href");
            if (url == null || url.equals("")) {
                throw new LineRouteParseException("No station url found");
            }
            list.add(new StationData(stationName, url));
        }

        return list;
    }

    /**
     * Parses chosen line route page and returns destination
     * @return name of a destination station
     */
    public String retrieveDestination(Document document) throws LineRouteParseException {
        String destinationName = null;
        Elements bullets = document.getElementsByTag("li");
        for (Element bullet : bullets) {
            Elements links = bullet.getElementsByTag("a");
            if (links.size() == 0) {
                destinationName = bullet.getElementsByTag("b").text();
            }
        }
        if (destinationName == null || destinationName.isEmpty()) {
            throw new LineRouteParseException("No destination found");
        }
        return destinationName;
    }

}
