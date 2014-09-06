package pl.jahu.mpk.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.utils.UrlResolver;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO Handle irregular routes (eg 183, 225)
// TODO Handle lines with one direction (405, 451) if required

/**
 * Created by hudzj on 8/1/2014.
 *
 * Returns list of stations on line's route in specified direction.
 *
 * Input (constructor):
 * - lineNo - line number (integer)
 * - direction - direction's index (integer beginning from 1)
 *
 * Output (parse() method):
 * - list of [station name, timetable url] String pairs
 */
public class LineRouteParser extends AbstractParser {

    private String destination;

    public LineRouteParser(LineNumber lineNo, int direction) throws TimetableNotFoundException, LineRouteParseException, UnsupportedLineNumberException {
        super(UrlResolver.getLineRouteUrl(lineNo, direction));
        this.destination = retrieveDestination();
    }

    public LineRouteParser(File file, String encoding) throws IOException, TimetableParseException, LineRouteParseException {
        super(file, encoding);
        this.destination = retrieveDestination();
    }

    public String getDestination() {
        return destination;
    }

    private String retrieveDestination() throws LineRouteParseException {
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

    /**
     * Parses chosen line route page
     * @return list of [station name, timetable url] String pairs
     * @throws LineRouteParseException
     */
    public List<String[]> parse() throws LineRouteParseException {
        List<String[]> list = new ArrayList<String[]>();
        Elements stations = document.getElementsByAttributeValue("target", "R");
        for (Element station : stations) {
            String stationName = station.text();
            if (stationName == null || stationName.equals("")) {
                throw new LineRouteParseException("No station name found");
            }
            String url = station.attributes().get("href");
            if (url == null || url.equals("")) {
                throw new LineRouteParseException("No station url found");
            }
            list.add(new String[]{stationName, url});
        }

        return list;
    }


}
