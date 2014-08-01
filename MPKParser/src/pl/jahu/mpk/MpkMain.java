package pl.jahu.mpk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.Departure;
import pl.jahu.mpk.entities.Station;
import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;
import pl.jahu.mpk.parser.LineNumbersResolver;
import pl.jahu.mpk.parser.TimetableParser;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;
import pl.jahu.mpk.parser.exceptions.UnsupportedDayTypesConfigurationException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class MpkMain {


    private final static String LINE_NUMBER_TOKEN = "@[line]";
    private final static String STOP_SEQ_TOKEN = "@[seg]";
    private final static String TIMETABLE_URL_PATTERN = "http://rozklady.mpk.krakow.pl/aktualne/"+LINE_NUMBER_TOKEN+"/"+LINE_NUMBER_TOKEN+"t"+STOP_SEQ_TOKEN+".htm";

    public static void main(String args[]) {

        try {
            LineNumbersResolver resolver = new LineNumbersResolver(true);
//            resolver.addVehicleType(VehicleTypes.BUS);
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

            for (Integer l : lineNumbersCandidates) {
                String line = (l < 10) ? "000" + l.toString() : (l < 100) ? "00" + l.toString() : "0" + l.toString();
                String url = TIMETABLE_URL_PATTERN.replace(LINE_NUMBER_TOKEN, line).replace(STOP_SEQ_TOKEN, "001");

                try {
                    TimetableParser parser = new TimetableParser(url);
                    System.out.println("[" + l.toString() + "] " + parser.getStopName());
                    parser.parse(null);
                } catch (TimetableNotFoundException e) {
//                    System.out.println("[" + l.toString() + "] not found");
                }
            }


//            TimetableParser parser = new TimetableParser("http://rozklady.mpk.krakow.pl/aktualne/0001/0001t001.htm");
//            Station station = new Station(parser.getStopName());
//            Map<DayTypes, List<Departure>> departures = parser.parse(station);
        } catch (TimetableParseException e) {
            e.printStackTrace();
        }

    }

}
