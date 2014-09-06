package pl.jahu.mpk.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;

/**
 * Created by hudzj on 8/1/2014.
 */
abstract class AbstractParser {

    protected Document document;
    private String url;

    /**
     * Creates parsers for specified URL
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
     * Creates parsers for specified file with timetable. Used for testing.
     */
    public AbstractParser(File file, String encoding) throws IOException, TimetableParseException {
        this.document = Jsoup.parse(file, encoding);
    }

    public String getUrl() {
        return url;
    }
}
