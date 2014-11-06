package pl.jahu.mpk.providers;

import org.jsoup.nodes.Document;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.StationData;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import javax.inject.Inject;
import java.util.List;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public abstract class TimetableProvider {

    @Inject
    LinesListParser linesListParser;

    @Inject
    LineRouteParser lineRouteParser;

    @Inject
    TimetableParser timetableParser;

    abstract Document getLinesListDocument() throws TimetableNotFoundException;

    abstract Document getLineRouteDocument(LineNumber lineNumber, int direction) throws UnsupportedLineNumberException, TimetableNotFoundException;

    abstract Document getTimetableDocument(LineNumber lineNumber, String page) throws UnsupportedLineNumberException, TimetableNotFoundException;

    public List<LineNumber> getLinesList() throws UnsupportedLineNumberException, NoDataProvidedException, TimetableNotFoundException {
        Document document = getLinesListDocument();
        return linesListParser.parse(document);
    }

    public List<StationData> getLineRoute(LineNumber lineNumber, int direction) throws LineRouteParseException, UnsupportedLineNumberException, TimetableNotFoundException {
        Document document = getLineRouteDocument(lineNumber, direction);
        return lineRouteParser.parse(document);
    }

    public String getLineRouteDestination(LineNumber lineNumber, int direction) throws LineRouteParseException, UnsupportedLineNumberException, TimetableNotFoundException {
        Document document = getLineRouteDocument(lineNumber, direction);
        return lineRouteParser.retrieveDestination(document);
    }

    public Timetable getTimetable(LineNumber lineNumber, String page) throws TimetableParseException, UnsupportedLineNumberException, TimetableNotFoundException {
        Document document = getTimetableDocument(lineNumber, page);
        return timetableParser.parse(document, lineNumber);
    }

}
