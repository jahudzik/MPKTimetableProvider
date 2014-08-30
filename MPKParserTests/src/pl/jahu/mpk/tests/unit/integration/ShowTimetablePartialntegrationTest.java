package pl.jahu.mpk.tests.unit.integration;

import org.junit.AfterClass;
import org.junit.Test;
import pl.jahu.mpk.parser.exceptions.LineRouteParseException;
import pl.jahu.mpk.parser.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parser.exceptions.TimetableParseException;
import pl.jahu.mpk.tests.integration.ShowTimetableIntegrationTest;

import static org.junit.Assert.fail;

/**
 * Created by jahudzik on 2014-08-03.
 *
 * Based on Scenario1ShowLineTimetable
 */
public class ShowTimetablePartialntegrationTest {

    @Test
     public void testShowingDailyBusesTimetable() {
        try {
            ShowTimetableIntegrationTest.showTimetable(180, 190, false);
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (LineRouteParseException e) {
            e.printStackTrace();
            fail();
        } catch (TimetableParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testShowingNigthlyBusesTimetable() {
        try {
            ShowTimetableIntegrationTest.showTimetable(600, 610, false);
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (LineRouteParseException e) {
            e.printStackTrace();
            fail();
        } catch (TimetableParseException e) {
            e.printStackTrace();
            fail();
        }
    }

}
