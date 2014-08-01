package pl.jahu.mpk.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParser {

    private static final String URL = "http://rozklady.mpk.krakow.pl/linie.aspx";

    private Document document;


    public LinesListParser() throws TimetableNotFoundException {
        try {
            this.document = Jsoup.connect(URL).get();
        } catch (IOException e) {
            if (e.toString().contains("Status=404")) {
                throw new TimetableNotFoundException();
            } else {
                e.printStackTrace();
            }
        }
    }

    public LinesListParser(File file, String encoding) throws IOException, TimetableParseException {
        this.document = Jsoup.parse(file, encoding);
    }

    public List<Integer> parse() {
        List<Integer> lines = new ArrayList<Integer>();
        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            String lineCandidate = link.text();
            try {
                int line = Integer.parseInt(lineCandidate);
                lines.add(line);
            } catch (NumberFormatException e) {}
        }
        return lines;
    }




}
