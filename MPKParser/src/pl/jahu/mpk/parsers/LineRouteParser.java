package pl.jahu.mpk.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LineRouteParser {

    /**
     * Parses chosen line route page
     * @return list of stations on the route without destination station
     */
    public List<StationData> parse(ParsableData parsableData) throws LineRouteParseException {
        List<StationData> list = new ArrayList<StationData>();
        Elements stations = parsableData.getDocument().getElementsByAttributeValue("target", "R");
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
    public String retrieveDestination(ParsableData parsableData) throws LineRouteParseException {
        String destinationName = null;
        Elements bullets = parsableData.getDocument().getElementsByTag("li");
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
