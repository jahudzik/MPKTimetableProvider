package pl.jahu.mpk.providers;

import org.joda.time.DateTime;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.Timetable;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.TimetableParser;
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


    abstract public ParsableData getUpdateInfoDocument() throws ParsableDataNotFoundException;

    abstract public ParsableData getLinesListDocument() throws ParsableDataNotFoundException;

    abstract public ParsableData getLineRouteDocument(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException;

    abstract public ParsableData getTimetableDocument(LineNumber lineNumber, int stationSequenceNumber) throws ParsableDataNotFoundException;

    public DateTime getLastUpdateDate() throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData data = getUpdateInfoDocument();
        return linesListParser.parseLastUpdateDate(data);
    }

    public List<Line> getLinesList() throws ParsableDataNotFoundException {
        ParsableData data = getLinesListDocument();
        return linesListParser.parseAll(data);
    }

    public List<Line> getChangedLinesList() throws ParsableDataNotFoundException {
        ParsableData data = getLinesListDocument();
        return linesListParser.parseChanged(data);
    }

    public List<StationData> getLineRoute(Line line, int direction) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData data = getLineRouteDocument(line.getNumber(), direction);
        return lineRouteParser.parse(line, data);
    }

    public String getLineRouteDestination(Line line, int direction) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData data = getLineRouteDocument(line.getNumber(), direction);
        return lineRouteParser.retrieveDestination(data);
    }

    public Timetable getTimetable(DateTime date, Line line, int stationSequenceNumber) throws ParsableDataNotFoundException, TimetableParseException {
        ParsableData parsableData = getTimetableDocument(line.getNumber(), stationSequenceNumber);
        boolean nightly = (line.getType().getReasonType() == ReasonTypes.NIGHTLY);
        List<DayType> dayTypeList = timetableParser.parseDayTypes(parsableData, nightly);
        int dayTypeIndex = matchDateWithDateType(date, dayTypeList, parsableData.getLocation());
        return timetableParser.parseDepartures(parsableData, dayTypeList, dayTypeIndex, line);
    }

    private int matchDateWithDateType(DateTime date, List<DayType> dayTypeList, String location) throws TimetableParseException {
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
