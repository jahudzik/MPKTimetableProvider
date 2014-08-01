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
import pl.jahu.mpk.parser.exceptions.UnsupportedDayTypesConfigurationException;

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
    private static final String DEPARTURES_TABLE_CLASS = "celldepart";
    private static final String HOUR_CELL_CLASS = "cellhour";
    private static final String MINUTE_CELL_CLASS = "cellmin";
    private static final String LEGEND_CELL_CLASS = "fontprzyp";
    private static final String NO_MINUTES_PATTERN = "-";

    private Document document;
    private String stopName;

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

        this.stopName = retrieveStopName();
    }

    public TimetableParser(File file, String encoding) throws IOException, TimetableParseException {
        this.document = Jsoup.parse(file, encoding);
        this.stopName = retrieveStopName();
    }


    private String retrieveStopName() throws TimetableParseException {
        Elements elementsByClass = document.getElementsByClass(STOP_NAME_CLASS);
        if (elementsByClass.size() == 0) {
            throw new TimetableParseException("Could not parse stop name");
        }
        return elementsByClass.get(0).text();
    }

    public String getStopName() {
        return stopName;
    }


    public Map<DayTypes, List<Departure>> parse(Station station) throws TimetableParseException {
        Map<DayTypes, List<Departure>> departures = new HashMap<DayTypes, List<Departure>>();
        Elements rows = document.getElementsByClass(DEPARTURES_TABLE_CLASS).get(0).getElementsByTag("tr");
        if (rows != null && rows.size() > 0) {
            Elements dayTypes = rows.get(0).children();
            if (dayTypes != null && dayTypes.size() > 0) {
                List<DayTypes> dayTypesList = retrieveDayTypesConfiguration(dayTypes);
                for (DayTypes type : dayTypesList) {
                    departures.put(type, new ArrayList<Departure>());
                }

                // first row is for day types, last row is for extra info
                for (int i = 1; i < rows.size()-1; i++) {
                    Elements hourCells = rows.get(i).getElementsByClass(HOUR_CELL_CLASS);
                    if (hourCells != null && hourCells.size() > 0) {
                        int hour = new Integer(hourCells.get(0).text());
                        Elements minCells = rows.get(i).getElementsByClass(MINUTE_CELL_CLASS);
                        if (minCells != null && minCells.size() == dayTypesList.size()) {
                            for (int j = 0; j < dayTypes.size(); j++) {
                                String minuteString = minCells.get(j).text();
                                if (!minuteString.equals(NO_MINUTES_PATTERN)) {
                                    String[] mins = minuteString.split(" ");
                                    for (String minString : mins) {
                                        try {
                                            int min = Integer.parseInt(minString);
                                            departures.get(dayTypesList.get(j)).add(new Departure(station, hour, min));
                                        } catch (NumberFormatException e) {
                                            int min = Integer.parseInt(minString.substring(0, 2));
                                            String letter = minString.substring(2);
                                            // find legend in the last row
                                            Elements legendCells = rows.get(rows.size() - 1).getElementsByClass(LEGEND_CELL_CLASS);
                                            if (legendCells.size() > 1) {
                                                System.out.println(")000000000000000000000");
                                            }
                                            for (int k = 0; k < legendCells.size(); k++) {
                                                String legend = legendCells.get(k).text();
                                                if (legend.substring(0, 1).equals(letter)) {
                                                    departures.get(dayTypesList.get(j)).add(new Departure(station, hour, min, legend.substring(4)));
                                                }
                                            }
                                        }
                                    }
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

    public static List<DayTypes> retrieveDayTypesConfiguration(Elements dayTypes) throws TimetableParseException {
        List<DayTypes> list = new ArrayList<DayTypes>();
        for (int i = 0; i < dayTypes.size(); i++) {
            String label = dayTypes.get(i).text();
            DayTypes dayType = ParserConstants.dayTypesNames.get(label);
            if (dayType != null) {
                list.add(dayType);
            } else {
                throw new TimetableParseException("Unsupported day type : '" + label + "'");
            }
        }
        return list;
    }

}
