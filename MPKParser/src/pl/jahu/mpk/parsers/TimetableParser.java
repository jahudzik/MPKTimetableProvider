package pl.jahu.mpk.parsers;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.utils.ParserConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Created by jahudzik on 2014-07-13.
 */
public class TimetableParser {

    private static final String STOP_NAME_CLASS = "fontstop";
    private static final String ROUTE_CELL_CLASS = "fontroute";
    private static final String DEPARTURES_TABLE_CLASS = "celldepart";
    private static final String HOUR_CELL_CLASS = "cellhour";
    private static final String MINUTE_CELL_CLASS = "cellmin";
    private static final String LEGEND_CELL_CLASS = "fontprzyp";
    private static final String NO_MINUTES_PATTERN = "-";

    private Elements legendCells;


    /**
     * Parser document and returns departures lists for each day type
     * @return Timetable object holding map with departures lists for each day type
     */
    public Timetable parse(ParsableData parsableData, LineNumber lineNumber) throws TimetableParseException {
        Document document = parsableData.getDocument();
        Map<DayTypes, List<Departure>> departures = new HashMap<DayTypes, List<Departure>>();
        Elements rows = document.getElementsByClass(DEPARTURES_TABLE_CLASS).get(0).getElementsByTag("tr");
        if (rows.size() > 0) {

            // parse day types
            Elements dayTypes = rows.get(0).children();
            if (dayTypes.size() > 0) {
                List<DayTypes> dayTypesList = retrieveDayTypesConfiguration(dayTypes, parsableData.getLocation());
                for (DayTypes type : dayTypesList) {
                    departures.put(type, new ArrayList<Departure>());
                }

                // get legend
                legendCells = rows.get(rows.size() - 1).getElementsByClass(LEGEND_CELL_CLASS);

                // first row is for day types, last row is for extra info - parse the rest (actual timetables)
                for (int i = 1; i < rows.size() - 1; i++) {
                    Elements hourCells = rows.get(i).getElementsByClass(HOUR_CELL_CLASS);
                    if (hourCells.size() > 0) {
                        int hour = new Integer(hourCells.get(0).text());
                        Elements minCells = rows.get(i).getElementsByClass(MINUTE_CELL_CLASS);
                        if (minCells.size() == dayTypesList.size()) {
                            for (int j = 0; j < dayTypes.size(); j++) {
                                String minuteString = minCells.get(j).text();
                                if (!minuteString.equals(NO_MINUTES_PATTERN)) {
                                    List<Departure> deps = buildDeparturesList(hour, minuteString, parsableData.getLocation());
                                    departures.get(dayTypesList.get(j)).addAll(deps);
                                }
                            }
                        } else {
                            throw new TimetableParseException("No minute cell found", parsableData.getLocation());
                        }
                    } else {
                        throw new TimetableParseException("No hour cell found", parsableData.getLocation());
                    }
                }
            } else {
                throw new TimetableParseException("No day type info found on the timetable", parsableData.getLocation());
            }
        } else {
            throw new TimetableParseException("No departure info found on the timetable", parsableData.getLocation());
        }

        String destStation;
        String route = retrieveSpecificCell(document, ROUTE_CELL_CLASS, "route", parsableData.getLocation());
        int in = route.lastIndexOf(" - ");
        if (in != -1) {
            destStation = route.substring(in + 3);
        } else {
            throw new TimetableParseException("Could not parse destStation", parsableData.getLocation());
        }

        String station = retrieveSpecificCell(document, STOP_NAME_CLASS, "stop name", parsableData.getLocation());

        return new Timetable(station, lineNumber, destStation, departures);
    }


    public static List<DayTypes> retrieveDayTypesConfiguration(Elements dayTypes, String location) throws TimetableParseException {
        List<DayTypes> list = new ArrayList<DayTypes>();
        for (Element dayType1 : dayTypes) {
            String label = dayType1.text();
            DayTypes dayType = ParserConstants.dayTypesNames.get(label);
            if (dayType != null) {
                list.add(dayType);
            } else {
                throw new TimetableParseException("Unsupported day type : '" + label + "'", location);
            }
        }
        return list;
    }

    /**
     * Returns list of departures for specified hour
     */
    private List<Departure> buildDeparturesList(int hour, String minuteString, String location) throws TimetableParseException {
        List<Departure> deps = new ArrayList<Departure>();
        String[] mins = minuteString.split(" ");
        for (String minString : mins) {
            try {
                int min = Integer.parseInt(minString);
                deps.add(new Departure(hour, min));
            } catch (NumberFormatException e) {
                int min = Integer.parseInt(minString.substring(0, 2));
                String legendLetters = minString.substring(2);
                int legendLettersCount = legendLetters.length();
                int legendsFound = 0;
                String[] legends = new String[legendLettersCount];
                if (legendCells != null) {
                    for (Element legendCell : legendCells) {
                        String legend = legendCell.text();
                        if (legendLetters.contains(legend.substring(0, 1))) {
                            legends[legendsFound++] = legend.substring(4);
                        }
                    }
                    deps.add(new Departure(hour, min, legends));
                }
                if (legendsFound != legendLettersCount) {
                    throw new TimetableParseException("No legend found", location);
                }
            }
        }
        return deps;
    }

    private String retrieveSpecificCell(Document document, String className, String contentDescription, String location) throws TimetableParseException {
        Elements elementsByClass = document.getElementsByClass(className);
        if (elementsByClass.size() == 0) {
            throw new TimetableParseException("Could not parse " + contentDescription, location);
        }
        return elementsByClass.get(0).text();
    }


}
