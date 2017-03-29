package pl.jahu.mpk.utils;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;

import static org.junit.Assert.assertEquals;

public class UrlResolverTest {

    @Test(expected = IllegalArgumentException.class)
    public void getLineRouteUrl_IllegalArgumentException1() {
        UrlResolver.getLineRouteUrl(new LineNumber(""), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLineRouteUrl_IllegalArgumentException2() {
        UrlResolver.getLineRouteUrl(new LineNumber("12345"), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getLineRouteUrl_IllegalArgumentException3() {
        UrlResolver.getLineRouteUrl(new LineNumber(-1), 1);
    }

    @Test
    public void getLineRouteUrl_test1() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber(4), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/0004/0004w001.htm", url);
    }

    @Test
    public void getLineRouteUrl_test2() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber(16), 2);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w002.htm", url);
    }

    @Test
    public void getLineRouteUrl_test3() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber(154), 4);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/0154/0154w004.htm", url);
    }

    @Test
    public void getLineRouteUrl_test4() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("3a"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/003a/003aw001.htm", url);
    }

    @Test
    public void getLineRouteUrl_test5() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("62a"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/062a/062aw001.htm", url);
    }

    @Test
    public void getLineRouteUrl_test6() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("502c"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/502c/502cw001.htm", url);
    }

    @Test
    public void getLineRouteUrl_test7() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("Z"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/000Z/000Zw001.htm", url);
    }

    @Test
    public void getLineLiteral_test1() {
        assertEquals("0001", UrlResolver.getLineLiteral(new LineNumber(1)));
    }

    @Test
    public void getLineLiteral_test2() {
        assertEquals("0045", UrlResolver.getLineLiteral(new LineNumber(45)));
    }

    @Test
    public void getLineLiteral_test3() {
        assertEquals("0967", UrlResolver.getLineLiteral(new LineNumber(967)));
    }

    @Test
    public void getLineLiteral_test4() {
        assertEquals("0001", UrlResolver.getLineLiteral(new LineNumber("1")));
    }

    @Test
    public void getLineLiteral_test5() {
        assertEquals("0045", UrlResolver.getLineLiteral(new LineNumber("45")));
    }

    @Test
    public void getLineLiteral_test6() {
        assertEquals("0967", UrlResolver.getLineLiteral(new LineNumber("967")));
    }

    @Test
    public void getLineLiteral_test7() {
        assertEquals("000A", UrlResolver.getLineLiteral(new LineNumber("A")));
    }

    @Test
    public void getLineLiteral_test8() {
        assertEquals("004B", UrlResolver.getLineLiteral(new LineNumber("4B")));
    }

    @Test
    public void getLineLiteral_test9() {
        assertEquals("091G", UrlResolver.getLineLiteral(new LineNumber("91G")));
    }

}