package pl.jahu.mpk.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;

/**
 * Created by hudzj on 8/1/2014.
 */
abstract class AbstractParser {

    protected Document document;
    private String url;

    /**
     * Creates parser for specified URL
     */
    public AbstractParser(String url) throws TimetableNotFoundException {
        try {
            this.document = Jsoup.connect(url).get();
            this.url = url;
        } catch (IOException e) {
            // TODO handle it properly
            if (e.toString().contains("Status=404")) {
                throw new TimetableNotFoundException();
            } else {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates parser for specified file with timetable. Used for testing.
     */
    public AbstractParser(File file, String encoding) throws IOException, TimetableParseException {
        this.document = Jsoup.parse(file, encoding);
    }

    public String getUrl() {
        return url;
    }
}
