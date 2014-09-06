package pl.jahu.mpk.tests.unit.parsers;

import org.junit.Test;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by hudzj on 8/1/2014.
 */
public class LinesListParserTest {

    @Test
    public void testAllLines() {
        File inputFile = new File("./MPKParserTests/res/lines.html");

        try {
            LinesListParser parser = new LinesListParser(inputFile, "iso-8859-2");
            List<LineNumber> lines = parser.parse();
            assertNotNull(lines);
            assertEquals(169, lines.size());
            assertTrue(lines.contains(new LineNumber(0)));
            assertTrue(lines.contains((new LineNumber(1))));
            assertTrue(lines.contains((new LineNumber(72))));
            assertTrue(lines.contains((new LineNumber(301))));
            assertTrue(lines.contains((new LineNumber(352))));
            assertTrue(lines.contains((new LineNumber(238))));
            assertTrue(lines.contains((new LineNumber(915))));

            assertFalse(lines.contains((new LineNumber(303))));
            assertFalse(lines.contains((new LineNumber(5))));
            assertFalse(lines.contains((new LineNumber(298))));
            assertFalse(lines.contains((new LineNumber(1000))));
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (TimetableParseException e) {
            fail(e.getMessage());
        } catch (NoDataProvidedException e) {
            fail(e.getMessage());
        } catch (UnsupportedLineNumberException e) {
            fail(e.getMessage());
        }
    }


}
