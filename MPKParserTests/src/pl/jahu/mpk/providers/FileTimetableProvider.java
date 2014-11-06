package pl.jahu.mpk.providers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.utils.LineNumbersResolver;

import java.io.File;
import java.io.IOException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class FileTimetableProvider extends TimetableProvider {

    public static final String LINES_LIST_FILE = "_lines.html";
    public static final String MENU_PAGE_FILE = "_menu.html";
    private static final String DIRECTION_TOKEN = "@[seg]";
    private static final String LINE_NUMBER_TOKEN = "@[line]";
    private static final String LINE_ROUTE_FILE_PATTERN = LINE_NUMBER_TOKEN + "w00" + DIRECTION_TOKEN + ".htm";
    private static final String PAGE_TOKEN = "@[page]";
    private static final String TIMETABLE_FILE_PATTERN = LINE_NUMBER_TOKEN + "t" + PAGE_TOKEN;
    public static final String FILE_ENCODING = "iso-8859-2";

    private final String filesLocation;


    public FileTimetableProvider(String filesLocation) {
        DaggerApplication.inject(this);
        this.filesLocation = filesLocation;
    }

    @Override
    public Document getLinesListDocument() throws TimetableNotFoundException {
        return getDocumentFromFile(LINES_LIST_FILE);
    }

    @Override
    public Document getLineRouteDocument(LineNumber lineNo, int direction) throws TimetableNotFoundException {
        return getDocumentFromFile(getLineRouteFileName(lineNo, direction));
    }

    @Override
    public Document getTimetableDocument(LineNumber lineNo, String page) throws TimetableNotFoundException {
        return getDocumentFromFile(page);
    }

    private Document getDocumentFromFile(String fileName) throws TimetableNotFoundException {
        try {
            return Jsoup.parse(new File(filesLocation + "/" + fileName), FILE_ENCODING);
        } catch (IOException e) {
            throw new TimetableNotFoundException(e.getMessage());
        }
    }

    private static String getLineRouteFileName(LineNumber lineNo, Integer direction) {
        return LINE_ROUTE_FILE_PATTERN.replace(LINE_NUMBER_TOKEN, LineNumbersResolver.getLineString(lineNo)).replace(DIRECTION_TOKEN, direction.toString());
    }

    public static String getStationTimetableFileName(LineNumber lineNo, int stationNo) {
        return TIMETABLE_FILE_PATTERN.replace(LINE_NUMBER_TOKEN, LineNumbersResolver.getLineString(lineNo)).replace(PAGE_TOKEN, LineNumbersResolver.getNumberLiteral(stationNo));
    }

}
