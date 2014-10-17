package pl.jahu.mpk.utils;

import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-08-30.
 */
public class UrlResolver {

    private final static String DIRECTION_TOKEN = "@[seg]";
    private final static String LINE_NUMBER_TOKEN = "@[line]";
    private final static String PAGE_TOKEN = "@[page]";

    private static final String LINE_ROUTE_URL_PATTERN = "http://rozklady.mpk.krakow.pl/aktualne/"+LINE_NUMBER_TOKEN+"/"+LINE_NUMBER_TOKEN+"w00"+DIRECTION_TOKEN+".htm";
    public static final String LINES_LIST_URL = "http://rozklady.mpk.krakow.pl/linie.aspx";
    public static final String TIMETABLE_MENU_URL = "http://rozklady.mpk.krakow.pl/menu.aspx";
    public static final String STATIONS_LIST_URL = "http://rozklady.mpk.krakow.pl/aktualne/przystan.htm";
    private static final String TIMETABLE_URL_PATTERN = "http://rozklady.mpk.krakow.pl/aktualne/" + LINE_NUMBER_TOKEN + "/" + PAGE_TOKEN;

    public static String getLineRouteUrl(LineNumber lineNo, Integer direction) throws UnsupportedLineNumberException {
        return LINE_ROUTE_URL_PATTERN.replace(LINE_NUMBER_TOKEN, getLineString(lineNo)).replace(DIRECTION_TOKEN, direction.toString());
    }

    public static String getStationTimetableUrl(LineNumber lineNo, String page) throws UnsupportedLineNumberException {
        return TIMETABLE_URL_PATTERN.replace(LINE_NUMBER_TOKEN, getLineString(lineNo)).replace(PAGE_TOKEN, page);
    }

    private static String getLineString(LineNumber lineNo) throws UnsupportedLineNumberException {
        if (lineNo == null) {
            throw new UnsupportedLineNumberException("null");
        }
        if (lineNo.isNumericOnly()) {
            if (lineNo.getNumeric() > 999) {
                throw new UnsupportedLineNumberException(lineNo.getLiteral());
            }
            return (lineNo.getNumeric() < 10) ? "000" + lineNo.toString() : (lineNo.getNumeric() < 100) ? "00" + lineNo.toString() : "0" + lineNo.toString();
        } else {
            switch (lineNo.getLiteral().length()) {
                case 1:
                    return "000" + lineNo.toString();
                case 2:
                    return "00" + lineNo.toString();
                case 3:
                    return "0" + lineNo.toString();
                case 4:
                    return lineNo.toString();
                default:
                    throw new UnsupportedLineNumberException(lineNo.getLiteral());
            }
        }
    }

}
