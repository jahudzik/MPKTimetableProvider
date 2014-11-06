package pl.jahu.mpk.tests.unit.utils;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.utils.UrlResolver;

import static org.junit.Assert.assertEquals;

public class UrlResolverTest {

    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedLineException1() {
        UrlResolver.getLineRouteUrl(new LineNumber(""), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedLineException2() {
        UrlResolver.getLineRouteUrl(new LineNumber("12345"), 1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedLineException3() {
        UrlResolver.getLineRouteUrl(new LineNumber(-1), 1);
    }

    @Test
    public void testGetLineRouteUrl1() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber(4), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/0004/0004w001.htm", url);
    }

    @Test
    public void testGetLineRouteUrl2() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber(16), 2);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w002.htm", url);
    }

    @Test
    public void testGetLineRouteUrl3() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber(154), 4);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/0154/0154w004.htm", url);
    }

    @Test
    public void testGetLineRouteUrl4() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("3a"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/003a/003aw001.htm", url);
    }

    @Test
    public void testGetLineRouteUrl5() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("62a"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/062a/062aw001.htm", url);
    }

    @Test
    public void testGetLineRouteUrl6() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("502c"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/502c/502cw001.htm", url);
    }

    @Test
    public void testGetLineRouteUrl7() {
        String url = UrlResolver.getLineRouteUrl(new LineNumber("Z"), 1);
        assertEquals("http://rozklady.mpk.krakow.pl/aktualne/000Z/000Zw001.htm", url);
    }

}