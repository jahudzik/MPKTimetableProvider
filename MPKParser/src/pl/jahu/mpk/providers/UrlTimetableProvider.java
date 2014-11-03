package pl.jahu.mpk.providers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.utils.UrlResolver;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.io.IOException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class UrlTimetableProvider extends TimetableProvider {

    public UrlTimetableProvider() {
        DaggerApplication.inject(this);
    }

    @Override
    Document getLinesListDocument() throws TimetableNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.LINES_LIST_URL);
    }

    @Override
    Document getLineRouteDocument(LineNumber lineNumber, int direction) throws UnsupportedLineNumberException, TimetableNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.getLineRouteUrl(lineNumber, direction));
    }

    @Override
    Document getTimetableDocument(LineNumber lineNumber, String page) throws UnsupportedLineNumberException, TimetableNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.getStationTimetableUrl(lineNumber, page));
    }

    private Document retrieveDocumentFromUrl(String url) throws TimetableNotFoundException {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            // TODO handle it properly
            if (e.toString().contains("Status=404")) {
                throw new TimetableNotFoundException();
            } else {
                e.printStackTrace();
            }
        }
        return document;
    }

}