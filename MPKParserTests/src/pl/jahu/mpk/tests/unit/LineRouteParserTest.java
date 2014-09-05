package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.parser.LineRouteParser;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LineRouteParserTest {

    @Test
    public void testRouteParser() {
        File inputFile = new File("./MPKParserTests/res/route.html");

        try {
            LineRouteParser parser = new LineRouteParser(inputFile, "iso-8859-2");
            assertEquals("Salwator", parser.getDestination());

            List<String[]> stations = parser.parse();

            assertNotNull(stations);
            assertEquals(31, stations.size());

            String[] station1 = stations.get(0);
            assertNotNull(station1);
            assertEquals(2, station1.length);
            assertEquals("Wzgórza Krzesławickie", station1[0]);
            assertEquals("0001t001.htm", station1[1]);

            String[] station2 = stations.get(30);
            assertNotNull(station2);
            assertEquals(2, station2.length);
            assertEquals("Salwator", station2[0]);
            assertEquals("0001t031.htm", station2[1]);

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        } catch (LineRouteParseException e) {
            fail();
        }
    }
}
