package pl.jahu.mpk.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.Station;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class TimetableParser {

    private static final String STOP_NAME_CLASS = "fontstop";
    private static final String ROUTE_CELL_CLASS = "fontroute";
    private static final String DEPARTURES_TABLE_CLASS = "celldepart";
    private static final String HOUR_CELL_CLASS = "cellhour";
    private static final String MINUTE_CELL_CLASS = "cellmin";
    private static final String LEGEND_CELL_CLASS = "fontprzyp";
    private static final String NO_MINUTES_PATTERN = "-";

    private final static String LINE_NUMBER_TOKEN = "@[line]";
    private final static String STOP_SEQ_TOKEN = "@[seg]";
    private final static String TIMETABLE_URL_PATTERN = "http://rozklady.mpk.krakow.pl/aktualne/" + LINE_NUMBER_TOKEN + "/" + LINE_NUMBER_TOKEN + "t" + STOP_SEQ_TOKEN + ".htm";

    private Document document;
    private String stopName;
    private String destination;
    private Elements legendCells;


    /**
     * Creates parser for specified URL
     */
    public TimetableParser(String url) throws TimetableNotFoundException, TimetableParseException {
        try {
            this.document = Jsoup.connect(url).get();
        } catch (IOException e) {
            if (e.toString().contains("Status=404")) {
                throw new TimetableNotFoundException();
            } else {
                e.printStackTrace();
            }
        }
        this.stopName = retrieveSpecificCell(STOP_NAME_CLASS, "stop name");
        this.destination = retrieveDestination();
    }

    /**
     * Creates parser for specified file with timetable. Used for testing.
     */
    public TimetableParser(File file, String encoding) throws IOException, TimetableParseException {
        this.document = Jsoup.parse(file, encoding);
        this.stopName = retrieveSpecificCell(STOP_NAME_CLASS, "stop name");
        this.destination = retrieveDestination();
    }


    private String retrieveDestination() throws TimetableParseException {
        String route = retrieveSpecificCell(ROUTE_CELL_CLASS, "route");
        int in = route.lastIndexOf(" - ");
        if (in != -1) {
            return route.substring(in+3);
        } else {
            throw new TimetableParseException("Could not parse destination");
        }
    }

    private String retrieveSpecificCell(String className, String contentDescription) throws TimetableParseException {
        Elements elementsByClass = document.getElementsByClass(className);
        if (elementsByClass.size() == 0) {
            throw new TimetableParseException("Could not parse " + contentDescription);
        }
        return elementsByClass.get(0).text();
    }


    public String getStopName() {
        return stopName;
    }

    public String getDestination() {
        return destination;
    }

    public Map<DayTypes, List<Departure>> parse(Station station) throws TimetableParseException {
        Map<DayTypes, List<Departure>> departures = new HashMap<DayTypes, List<Departure>>();
        Elements rows = document.getElementsByClass(DEPARTURES_TABLE_CLASS).get(0).getElementsByTag("tr");
        if (rows != null && rows.size() > 0) {

            // parse day types
            Elements dayTypes = rows.get(0).children();
            if (dayTypes != null && dayTypes.size() > 0) {
                List<DayTypes> dayTypesList = retrieveDayTypesConfiguration(dayTypes);
                for (DayTypes type : dayTypesList) {
                    departures.put(type, new ArrayList<Departure>());
                }

                // get legend
                legendCells = rows.get(rows.size() - 1).getElementsByClass(LEGEND_CELL_CLASS);

                // first row is for day types, last row is for extra info - parse the rest (actual timetables)
                for (int i = 1; i < rows.size() - 1; i++) {
                    Elements hourCells = rows.get(i).getElementsByClass(HOUR_CELL_CLASS);
                    if (hourCells != null && hourCells.size() > 0) {
                        int hour = new Integer(hourCells.get(0).text());
                        Elements minCells = rows.get(i).getElementsByClass(MINUTE_CELL_CLASS);
                        if (minCells != null && minCells.size() == dayTypesList.size()) {
                            for (int j = 0; j < dayTypes.size(); j++) {
                                String minuteString = minCells.get(j).text();
                                if (!minuteString.equals(NO_MINUTES_PATTERN)) {
                                    List<Departure> deps = buildDeparturesList(station, hour, minuteString);
                                    departures.get(dayTypesList.get(j)).addAll(deps);
                                }
                            }
                        } else {
                            throw new TimetableParseException("No minute cell found");
                        }
                    } else {
                        throw new TimetableParseException("No hour cell found");
                    }
                }
            } else {
                throw new TimetableParseException("No day type info found on the timetable");
            }
        } else {
            throw new TimetableParseException("No departure info found on the timetable");
        }

        return departures;
    }

    /**
     * Returns list of departures for specified hour
     */
    private List<Departure> buildDeparturesList(Station station, int hour, String minuteString) throws TimetableParseException {
        List<Departure> deps = new ArrayList<Departure>();
        String[] mins = minuteString.split(" ");
        for (String minString : mins) {
            try {
                int min = Integer.parseInt(minString);
                deps.add(new Departure(station, hour, min));
            } catch (NumberFormatException e) {
                int min = Integer.parseInt(minString.substring(0, 2));
                String letter = minString.substring(2);
                boolean legendFound = false;
                if (legendCells != null) {
                    for (Element legendCell : legendCells) {
                        String legend = legendCell.text();
                        if (legend.substring(0, 1).equals(letter)) {
                            deps.add(new Departure(station, hour, min, legend.substring(4)));
                            legendFound = true;
                        }
                    }
                }
                if (!legendFound) {
                    throw new TimetableParseException("No legend found");
                }
            }
        }
        return deps;
    }

    public static List<DayTypes> retrieveDayTypesConfiguration(Elements dayTypes) throws TimetableParseException {
        List<DayTypes> list = new ArrayList<DayTypes>();
        for (Element dayType1 : dayTypes) {
            String label = dayType1.text();
            DayTypes dayType = ParserConstants.dayTypesNames.get(label);
            if (dayType != null) {
                list.add(dayType);
            } else {
                throw new TimetableParseException("Unsupported day type : '" + label + "'");
            }
        }
        return list;
    }


    public static String getStationTimetableUrl(Integer lineNo, Integer stationSeq) {
        String line = (lineNo < 10) ? "000" + lineNo.toString() : (lineNo < 100) ? "00" + lineNo.toString() : "0" + lineNo.toString();
        String seq = (stationSeq < 10) ? "00" + stationSeq.toString() : "0" + stationSeq.toString();
        return TIMETABLE_URL_PATTERN.replace(LINE_NUMBER_TOKEN, line).replace(STOP_SEQ_TOKEN, seq);
    }

}
