package pl.jahu.mpk.providers;

import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.utils.DownloadUtils;
import pl.jahu.mpk.utils.UrlResolver;

import javax.inject.Inject;
import java.io.IOException;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class UrlTimetableProvider extends TimetableProvider {

    @Inject DownloadUtils downloadUtils;

    public UrlTimetableProvider() {
        DaggerApplication.inject(this);
    }

    @Override
    public ParsableData getUpdateInfoDocument() throws ParsableDataNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.UPDATE_INFO_URL);
    }

    @Override
    public ParsableData getLinesListDocument() throws ParsableDataNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.LINES_LIST_URL);
    }

    @Override
    public ParsableData getLineRouteDocument(LineNumber lineNumber, int direction) throws ParsableDataNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.getLineRouteUrl(lineNumber, direction));
    }

    @Override
    public ParsableData getTimetableDocument(LineNumber lineNumber, int sequenceNumber) throws ParsableDataNotFoundException {
        return retrieveDocumentFromUrl(UrlResolver.getStationTimetableUrl(lineNumber, sequenceNumber));
    }

    private ParsableData retrieveDocumentFromUrl(String url) throws ParsableDataNotFoundException {
        ParsableData parsableData = null;
        try {
            parsableData = downloadUtils.downloadJsoupDocument(url);
        } catch (IOException e) {
            // TODO handle it properly
            if (e.toString().contains("Status=404")) {
                throw new ParsableDataNotFoundException(e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
        return parsableData;
    }

}
