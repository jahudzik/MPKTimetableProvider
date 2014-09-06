package pl.jahu.mpk.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;
import pl.jahu.mpk.parser.utils.UrlResolver;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 *
 * Output (parse() method):
 * - list of lines numbers
 */
public class LinesListParser extends AbstractParser {

    public LinesListParser() throws TimetableNotFoundException {
        super(UrlResolver.LINES_LIST_URL);
    }

    public LinesListParser(File file, String encoding) throws IOException, TimetableParseException {
        super(file, encoding);
    }

    public List<LineNumber> parse() throws NoDataProvidedException, UnsupportedLineNumberException {
        List<LineNumber> lines = new ArrayList<LineNumber>();
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            // ensure this is a link to line details - it should have "[line_number]rw" substring (ex http://rozklady.mpk.krakow.pl/aktualne/0164/0164rw01.htm)
            String href = link.attr("href");
            if (href != null && href.contains(link.text() + "rw")) {
                lines.add(new LineNumber(link.text()));
            }
        }
        return lines;
    }




}
