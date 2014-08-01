package pl.jahu.main;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class HelloWorld {


    public static void main(String args[]) {
        String url = "http://rozklady.mpk.krakow.pl/aktualne/0001/0001t001.htm";
        try {
            Document document = Jsoup.connect(url).get();
            Elements fontstop = document.getElementsByClass("fontstopa");
            Element element = fontstop.get(0);
            String stopName = document.getElementsByClass("fontstop").get(0).text();
            int a = 3;
        } catch (IOException e) {
        }
    }
}
