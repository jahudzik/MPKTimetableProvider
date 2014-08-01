package pl.jahu.mpk.tests;

import org.junit.Test;
import pl.jahu.mpk.parser.LinesListParser;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;

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
            List<Integer> lines = parser.parse();
            assertNotNull(lines);
            assertEquals(169, lines.size());
        } catch (IOException e) {
            fail();
        } catch (TimetableParseException e) {
            fail();
        }
    }


}
