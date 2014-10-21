package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Test;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.StationData;
import pl.jahu.mpk.parsers.exceptions.LineRouteParseException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;

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

            List<StationData> stations = parser.parse();

            assertNotNull(stations);
            assertEquals(31, stations.size());

            StationData station1 = stations.get(0);
            assertNotNull(station1);
            assertEquals("Wzgórza Krzesławickie", station1.getName());
            assertEquals("0001t001.htm", station1.getAddress());

            StationData station2 = stations.get(30);
            assertNotNull(station2);
            assertEquals("Salwator", station2.getName());
            assertEquals("0001t031.htm", station2.getAddress());

        } catch (TimetableParseException e) {
            fail();
        } catch (IOException e) {
            fail();
        } catch (LineRouteParseException e) {
            fail();
        }
    }
}
