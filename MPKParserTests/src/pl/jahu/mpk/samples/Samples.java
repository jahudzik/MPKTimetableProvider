package pl.jahu.mpk.samples;

import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;
import pl.jahu.mpk.parser.LineNumbersResolver;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 */
public class Samples {

    public static void parseFilteredLinesTimetables() {
        try {
            LineNumbersResolver resolver = new LineNumbersResolver(true);
            resolver.addVehicleType(VehicleTypes.BUS);
            resolver.addVehicleType(VehicleTypes.TRAM);
            resolver.addAreaType(AreaTypes.CITY);
            resolver.addAreaType(AreaTypes.AGGLOMERATION);
            resolver.addReasonType(ReasonTypes.STANDARD);
            resolver.addReasonType(ReasonTypes.SPECIAL);
            resolver.addReasonType(ReasonTypes.NIGHTLY);
            resolver.addReasonType(ReasonTypes.ADDITIONAL);
            resolver.addReasonType(ReasonTypes.RAPID);
            resolver.addReasonType(ReasonTypes.REPLACEMENT);
            List<Integer> lineNumbersCandidates = resolver.getLineNumbersCandidates();

            for (Integer lineCandidate : lineNumbersCandidates) {
                String url = TimetableParser.getStationTimetableUrl(lineCandidate, 1);

                try {
                    TimetableParser parser = new TimetableParser(url);
                    System.out.println("[" + lineCandidate.toString() + "] " + parser.getStopName());
                    parser.parse(null);
                } catch (TimetableNotFoundException e) {}
            }
        } catch (TimetableParseException e) {
            e.printStackTrace();
        }
    }


    public static void parseAllLines() {
        try {
            LinesListParser parser = new LinesListParser();
            List<Integer> lines = parser.parse();
            for (int line : lines) {
                System.out.println(line);
            }
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        }
    }

}