package pl.jahu.mpk.utils;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class DownloadUtils {

    public void downloadUrl(String url, String destFileName) throws TimetableNotFoundException {
        try {
            System.out.println(url);
            FileUtils.copyURLToFile(new URL(url), new File(destFileName));
        } catch (IOException e) {
            throw new TimetableNotFoundException(e.getMessage());
        }
    }

    public ParsableData downloadJsoupDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return new ParsableData(document, url);
    }

}
