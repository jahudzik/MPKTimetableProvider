package pl.jahu.mpk.samples;

import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.LineRouteParser;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;
import pl.jahu.mpk.parser.exceptions.UnsupportedDayTypesConfigurationException;
import pl.jahu.mpk.parser.utils.TimeUtils;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.util.*;

/**
 * Created by hudzj on 8/1/2014.
 */
public class Scenario1ShowLineTimetable {


    public static void execute() {
        LineNumber chosenLine; // 183, 225
        String chosenDestination;
        String[] chosenStation;
        Random rand = new Random();

        try {
            // show all lines
            LinesListParser linesListParser = new LinesListParser();
            List<LineNumber> lines = linesListParser.parse();
            Collections.sort(lines);

            System.out.println("### Choose line:");
            System.out.print("### ");
            for (LineNumber line : lines) {
                System.out.print(line + " ");
            }
            chosenLine = lines.get(rand.nextInt(lines.size()));
            System.out.println("\n[User chooses line " + chosenLine + "]");

            // get routes for chosen line
            Map<String, LineRouteParser> destinations = new HashMap<String, LineRouteParser>();
            for (int i = 1; i < 10; i++) {
                try {
                    LineRouteParser routeParser = new LineRouteParser(chosenLine, i);
                    destinations.put(routeParser.getDestination(), routeParser);
                } catch (TimetableNotFoundException e) {
                    break;
                } catch (LineRouteParseException e) {
                    e.printStackTrace();
                }
            }

            List<String> destList = new ArrayList<String>(destinations.keySet());
            Collections.sort(destList);
            System.out.println("\n### Choose direction:");
            for (String dest : destList) {
                System.out.println("### - " + dest);
            }
            chosenDestination = destList.get(rand.nextInt(destList.size()));
            System.out.println("[User chooses '" + chosenDestination + "' destination]");


            List<String[]> route = null;
            try {
                LineRouteParser chosenRouteParser = destinations.get(chosenDestination);
                route = chosenRouteParser.parse();
            } catch (LineRouteParseException e) {
                e.printStackTrace();
            }

            System.out.println("\n### Choose station:");
            assert route != null;
            for (String[] stationInfo : route) {
                System.out.println("### - " + stationInfo[0]);
            }

            chosenStation = route.get(rand.nextInt(route.size()));
            System.out.println("[User choses '" + chosenStation[0] + "' station]");

            List<Departure> departuresForToday = null;
            DayTypes todayType = null;

            TimetableParser timetableParser = new TimetableParser(chosenLine, chosenStation[1]);
            Map<DayTypes, List<Departure>> timetables = timetableParser.parse().getDepartures();
            for (DayTypes dayType : timetables.keySet()) {
                if (TimeUtils.validateDayTypeForToday(dayType)) {
                    departuresForToday = timetables.get(dayType);
                    todayType = dayType;
                    break;
                }
            }

            System.out.println("\n### Departures for today (" + todayType + ") ");
            System.out.print("### ");
            assert departuresForToday != null;
            for (Departure dep : departuresForToday) {
                System.out.print(dep.getTime() + "  ");
            }
            System.out.println();
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedLineNumberException e) {
            e.printStackTrace();
        } catch (NoDataProvidedException e) {
            e.printStackTrace();
        } catch (UnsupportedDayTypesConfigurationException e) {
            e.printStackTrace();
        } catch (TimetableParseException e) {
            e.printStackTrace();
        }

    }

}
