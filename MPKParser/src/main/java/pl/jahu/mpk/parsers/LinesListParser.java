package pl.jahu.mpk.parsers;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineInfo;
import pl.jahu.mpk.enums.Areas;
import pl.jahu.mpk.enums.LineTypes;
import pl.jahu.mpk.enums.Vehicles;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParser {

    private static final Pattern DATE_PATTERN = Pattern.compile("(\\d{2}).(\\d{2}).(\\d{4})");

    /**
     * Parses lines list page and return list of lines
     *
     */
    public List<Line> parseAll(ParsableData parsableData) {
        return parse(parsableData, null);
    }

    public List<Line> parseChanged(ParsableData parsableData) {
        return parse(parsableData, "special");
    }

    public DateTime parseLastUpdateDate(ParsableData parsableData) throws TimetableParseException {
        Elements boldElements = parsableData.getDocument().getElementsByTag("b");
        for (Element boldElement : boldElements) {
            String text = boldElement.text();
            if (text.contains("ostatnia aktualizacja:")) {
                Matcher matcher = DATE_PATTERN.matcher(text);
                if (matcher.find()) {
                    return DateTimeFormat.forPattern("dd.MM.yyy").parseDateTime(matcher.group(0));
                }
            }
        }
        throw new TimetableParseException("Did not find last update date", parsableData.getLocation());
    }

    private List<Line> parse(ParsableData parsableData, String tagClass) {
        List<Line> lineNumbers = new ArrayList<>();

        Elements tables = parsableData.getDocument().getElementsByTag("table");
        for (Element table : tables) {
            Elements lineTypeCells = table.getElementsByTag("th");
            Elements lineNumbersCells = table.getElementsByTag("td");

            if (lineTypeCells.size() > 0 && lineNumbersCells.size() > 0) {
                String lineTypeName = lineTypeCells.get(0).text();
                LineInfo lineInfo = retrieveLineType(lineTypeName);

                Elements lineNumberElements = (tagClass == null) ? lineNumbersCells.get(0).getElementsByTag("a") : lineNumbersCells.get(0).getElementsByClass(tagClass);
                lineNumbers.addAll(getLineNumbers(lineNumberElements, lineInfo));
            }
        }
        return lineNumbers;
    }

    private List<Line> getLineNumbers(Elements links, LineInfo lineInfo) {
        List<Line> lines = new ArrayList<>();
        for (Element link : links) {
            // ensure this is a link to line details - it should have "[line_number]rw" substring (ex http://rozklady.mpk.krakow.pl/aktualne/0164/0164rw01.htm)
            String href = link.attr("href");
            if (href != null && href.contains(link.text() + "rw")) {
                lines.add(new Line(link.text(), lineInfo));
            }
        }
        return lines;
    }

    private static LineInfo retrieveLineType(String lineTypeName) {
        Vehicles vehicleType = lineTypeName.contains("tramwajowe") ? Vehicles.TRAM : Vehicles.BUS;
        Areas areaType = lineTypeName.contains("aglomeracyjne") ? Areas.AGGLOMERATION : Areas.CITY;
        LineTypes lineType = LineTypes.STANDARD;
        if (lineTypeName.contains("zastepcze")) {
            lineType = LineTypes.REPLACEMENT;
        }
        if (lineTypeName.contains("specjalne")) {
            lineType = LineTypes.SPECIAL;
        }
        if (lineTypeName.contains("nocne")) {
            lineType = LineTypes.NIGHTLY;
        }
        if (lineTypeName.contains("przyspieszone")) {
            lineType = LineTypes.RAPID;
        }
        if (lineTypeName.contains("wspomagajace")) {
            lineType = LineTypes.ADDITIONAL;
        }
        return new LineInfo(vehicleType, lineType, areaType);
    }

}
