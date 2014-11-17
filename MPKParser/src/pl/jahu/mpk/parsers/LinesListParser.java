package pl.jahu.mpk.parsers;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.entities.LineType;
import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;
import pl.jahu.mpk.parsers.data.ParsableData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParser {

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

    private List<Line> parse(ParsableData parsableData, String tagClass) {
        List<Line> lineNumbers = new ArrayList<>();

        Elements tables = parsableData.getDocument().getElementsByTag("table");
        for (Element table : tables) {
            Elements lineTypeCells = table.getElementsByTag("th");
            Elements lineNumbersCells = table.getElementsByTag("td");

            if (lineTypeCells.size() > 0 && lineNumbersCells.size() > 0) {
                String lineTypeName = lineTypeCells.get(0).text();
                LineType lineType = retrieveLineType(lineTypeName);

                Elements lineNumberElements = (tagClass == null) ? lineNumbersCells.get(0).getElementsByTag("a") : lineNumbersCells.get(0).getElementsByClass(tagClass);
                lineNumbers.addAll(getLineNumbers(lineNumberElements, lineType));
            }
        }
        return lineNumbers;
    }

    private List<Line> getLineNumbers(Elements links, LineType lineType) {
        List<Line> lines = new ArrayList<>();
        for (Element link : links) {
            // ensure this is a link to line details - it should have "[line_number]rw" substring (ex http://rozklady.mpk.krakow.pl/aktualne/0164/0164rw01.htm)
            String href = link.attr("href");
            if (href != null && href.contains(link.text() + "rw")) {
                LineNumber lineNumber = new LineNumber(link.text());
                lines.add(new Line(lineNumber, lineType));
            }
        }
        return lines;
    }


    private static LineType retrieveLineType(String lineTypeName) {
        VehicleTypes vehicleType = lineTypeName.contains("tramwajowe") ? VehicleTypes.TRAM : VehicleTypes.BUS;
        AreaTypes areaType = lineTypeName.contains("aglomeracyjne") ? AreaTypes.AGGLOMERATION : AreaTypes.CITY;
        ReasonTypes reasonType = ReasonTypes.STANDARD;
        if (lineTypeName.contains("zastepcze")) {
            reasonType = ReasonTypes.REPLACEMENT;
        }
        if (lineTypeName.contains("specjalne")) {
            reasonType = ReasonTypes.SPECIAL;
        }
        if (lineTypeName.contains("nocne")) {
            reasonType = ReasonTypes.NIGHTLY;
        }
        if (lineTypeName.contains("przyspieszone")) {
            reasonType = ReasonTypes.RAPID;
        }
        if (lineTypeName.contains("wspomagajace")) {
            reasonType = ReasonTypes.ADDITIONAL;
        }
        return new LineType(vehicleType, reasonType, areaType);
    }

}
