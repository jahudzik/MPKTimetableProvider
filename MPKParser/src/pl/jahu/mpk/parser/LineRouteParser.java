package pl.jahu.mpk.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LineRouteParser extends AbstractParser {

    private final static String LINE_NUMBER_TOKEN = "@[line]";
    private final static String DIRECTION_TOKEN = "@[seg]";
    private static final String LINE_ROUTE_URL_PATTERN = "http://rozklady.mpk.krakow.pl/aktualne/"+LINE_NUMBER_TOKEN+"/"+LINE_NUMBER_TOKEN+"w00"+DIRECTION_TOKEN+".htm";

    public LineRouteParser(String url) throws TimetableNotFoundException {
        super(url);
    }

    public LineRouteParser(File file, String encoding) throws IOException, TimetableParseException {
        super(file, encoding);
    }

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

    public static String getLineRouteUrl(Integer lineNo, Integer direction) {
        String line = (lineNo < 10) ? "000" + lineNo.toString() : (lineNo < 100) ? "00" + lineNo.toString() : "0" + lineNo.toString();
        return LINE_ROUTE_URL_PATTERN.replace(LINE_NUMBER_TOKEN, line).replace(DIRECTION_TOKEN, direction.toString());
    }


}
