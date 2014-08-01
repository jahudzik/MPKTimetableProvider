package pl.jahu.mpk.samples;

import pl.jahu.mpk.parser.LineRouteParser;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 */
public class Scenario1ShowLineTimetable {


    public static void execute() {
        try {
            // show all lines
            LinesListParser linesListParser = new LinesListParser();
            List<Integer> lines = linesListParser.parse();
            Collections.sort(lines);

            System.out.println("### Choose line:");
            for (int line : lines) {
                System.out.print(line + " ");
            }
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n");
        System.out.println("[User chooses line 142]");
        int line = 142;

        // get routes for chosen line
        for (int i = 1; i < 10; i++) {
            String url = LineRouteParser.getLineRouteUrl(line, i);
            try {
                LineRouteParser routeParser = new LineRouteParser(url);
                List<String[]> route = routeParser.parse();
                String[] routeInfo = route.get(route.size()-1);
            } catch (TimetableNotFoundException e) {
                break;
            } catch (LineRouteParseException e) {
                e.printStackTrace();
            }

        }

        System.out.println("\n\n");
        System.out.println("### Choose direction:");

    }

}
