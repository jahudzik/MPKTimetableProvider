package pl.jahu.mpk.providers;

import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.parsers.*;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;

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

    abstract ParsableData getLinesListDocument() throws ParsableDataNotFoundException;

    abstract ParsableData getLineRouteDocument(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException;

    abstract ParsableData getTimetableDocument(LineNumber lineNumber, int stationSequenceNumber) throws ParsableDataNotFoundException;

    public List<LineNumber> getLinesList() throws ParsableDataNotFoundException {
        ParsableData data = getLinesListDocument();
        return linesListParser.parse(data);
    }

    public List<StationData> getLineRoute(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData data = getLineRouteDocument(lineNumber, direction);
        return lineRouteParser.parse(lineNumber, data);
    }

    public String getLineRouteDestination(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData data = getLineRouteDocument(lineNumber, direction);
        return lineRouteParser.retrieveDestination(data);
    }

    public Timetable getTimetable(LineNumber lineNumber, int stationSequenceNumber) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData data = getTimetableDocument(lineNumber, stationSequenceNumber);
        return timetableParser.parse(data, lineNumber);
    }

}
