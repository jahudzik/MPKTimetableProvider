package pl.jahu.mpk.providers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.utils.UrlResolver;

import java.io.File;
import java.io.IOException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class FileTimetableProvider extends TimetableProvider {

    public static final String LINES_LIST_FILE = "_lines.html";
    public static final String FILE_ENCODING = "iso-8859-2";

    private final String filesLocation;


    public FileTimetableProvider(String filesLocation) {
        DaggerApplication.inject(this);
        this.filesLocation = filesLocation;
    }

    @Override
    public ParsableData getLinesListDocument() throws ParsableDataNotFoundException {
        return getDocumentFromFile(LINES_LIST_FILE);
    }

    @Override
    public ParsableData getLineRouteDocument(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException {
        return getDocumentFromFile(UrlResolver.getLineRouteFileName(lineNumber, direction));
    }

    @Override
    public ParsableData getTimetableDocument(LineNumber lineNumber, int sequenceNumber) throws ParsableDataNotFoundException {
        return getDocumentFromFile(UrlResolver.getStationTimetableFileName(lineNumber, sequenceNumber));
    }

    private ParsableData getDocumentFromFile(String fileName) throws ParsableDataNotFoundException {
        try {
            String fileLocation = filesLocation + "/" + fileName;
            Document document = Jsoup.parse(new File(fileLocation), FILE_ENCODING);
            return new ParsableData(document, fileLocation);
        } catch (IOException e) {
            throw new ParsableDataNotFoundException(e.getMessage());
        }
    }

}
