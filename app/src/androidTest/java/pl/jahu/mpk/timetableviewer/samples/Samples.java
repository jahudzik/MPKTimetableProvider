package pl.jahu.mpk.timetableviewer.samples;

/**
 * Created by hudzj on 8/1/2014.
 */
public class Samples {

//    public static void parseFilteredLinesTimetables() {
//        try {
//            LineNumbersResolver resolver = new LineNumbersResolver(true);
//            resolver.addVehicleType(VehicleTypes.BUS);
//            resolver.addVehicleType(VehicleTypes.TRAM);
//            resolver.addAreaType(AreaTypes.CITY);
//            resolver.addAreaType(AreaTypes.AGGLOMERATION);
//            resolver.addReasonType(ReasonTypes.STANDARD);
//            resolver.addReasonType(ReasonTypes.SPECIAL);
//            resolver.addReasonType(ReasonTypes.NIGHTLY);
//            resolver.addReasonType(ReasonTypes.ADDITIONAL);
//            resolver.addReasonType(ReasonTypes.RAPID);
//            resolver.addReasonType(ReasonTypes.REPLACEMENT);
//            List<Integer> lineNumbersCandidates = resolver.getLineNumbersCandidates();
//
//            for (Integer lineCandidate : lineNumbersCandidates) {
//                String url = TimetableParser.getStationTimetableUrl(lineCandidate, 1);
//
//                try {
//                    TimetableParser parser = new TimetableParser(url);
//                    System.out.println("[" + lineCandidate.toString() + "] " + parser.getStopName());
//                    parser.parse(null);
//                } catch (TimetableNotFoundException e) {}
//            }
//        } catch (TimetableParseException e) {
//            e.printStackTrace();
//        }
//    }


//    public static void parseAllLines() {
//        try {
//            LinesListParser parser = new LinesListParser();
//            List<Integer> lines = parser.parse();
//            for (int line : lines) {
//                System.out.println(line);
//            }
//        } catch (TimetableNotFoundException e) {
//            e.printStackTrace();
//        }
//    }


//    public static void parseLineRoute() {
//        try {
//            String lineRouteUrl = LineRouteParser.getLineRouteUrl(10, 1);
//            LineRouteParser parser = new LineRouteParser(lineRouteUrl);
//            List<String[]> list = parser.parse();
//            for (String[] item : list) {
//                System.out.println(item[0] + ": " + item[1]);
//            }
//        } catch (TimetableNotFoundException e) {
//            e.printStackTrace();
//        } catch (LineRouteParseException e) {
//            e.printStackTrace();
//        }
//    }

}
