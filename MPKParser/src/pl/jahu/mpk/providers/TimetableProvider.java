package pl.jahu.mpk.providers;

import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;

import javax.inject.Inject;
import java.util.Date;
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

    abstract public ParsableData getLinesListDocument() throws ParsableDataNotFoundException;

    abstract public ParsableData getLineRouteDocument(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException;

    abstract public ParsableData getTimetableDocument(LineNumber lineNumber, int stationSequenceNumber) throws ParsableDataNotFoundException;

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

    public Timetable getTimetable(Date date, LineNumber lineNumber, int stationSequenceNumber) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData parsableData = getTimetableDocument(lineNumber, stationSequenceNumber);
        List<DayType> dayTypeList = timetableParser.parseDayTypes(parsableData, lineNumber);
        int dayTypeIndex = matchDateWithDateType(date, dayTypeList, parsableData.getLocation());
        return timetableParser.parseDepartures(parsableData, dayTypeList, dayTypeIndex, lineNumber);
    }

    private int matchDateWithDateType(Date date, List<DayType> dayTypeList, String location) throws TimetableParseException {
        for (int i = 0; i < dayTypeList.size(); i++) {
            if (dayTypeList.get(i).matches(date)) {
                return i;
            }
        }
        throw new TimetableParseException("Could not match date (" + date + ") with parsed day types: " + dayTypesListToString(dayTypeList), location);
    }

    public static String dayTypesListToString(List<DayType> dayTypeList) {
        StringBuilder sb = new StringBuilder("(");
        for (DayType dayType : dayTypeList) {
            sb.append(dayType.toString());
        }
        sb.append(")");
        return sb.toString();
    }

}
