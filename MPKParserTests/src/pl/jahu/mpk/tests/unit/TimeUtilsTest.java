package pl.jahu.mpk.tests.unit;

import org.junit.Test;
import pl.jahu.mpk.enums.DayTypes;
import pl.jahu.mpk.parser.exceptions.UnsupportedDayTypesConfigurationException;
import pl.jahu.mpk.parser.utils.TimeUtils;

import java.util.Calendar;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

// TODO Handle WEEKEND_NIGHTS properly

/**
 * Created by jahudzik on 2014-08-03.
 */
public class TimeUtilsTest {


    @Test
    public void testSunday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.SUNDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.SUNDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.SUNDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.SUNDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.SUNDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.SUNDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test
    public void testMonday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.MONDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.MONDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.MONDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.MONDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.MONDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.MONDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test
    public void testThursday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.THURSDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.THURSDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.THURSDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.THURSDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.THURSDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.THURSDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test
    public void testFriday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.FRIDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.FRIDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.FRIDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }


    @Test
    public void testSaturday() {
        try {
            assertTrue(TimeUtils.validateDayType(DayTypes.EVERYDAY, Calendar.SATURDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.MONDAY_TO_THURSDAY, Calendar.SATURDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.SATURDAY, Calendar.SATURDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.SUNDAY, Calendar.SATURDAY));
            assertFalse(TimeUtils.validateDayType(DayTypes.WEEKDAY, Calendar.SATURDAY));
            assertTrue(TimeUtils.validateDayType(DayTypes.WEEKEND_NIGHTS, Calendar.SATURDAY));
        } catch (UnsupportedDayTypesConfigurationException e) {
            fail();
        }
    }

    @Test(expected = UnsupportedDayTypesConfigurationException.class)
    public void testNull() throws UnsupportedDayTypesConfigurationException {
        assertTrue(TimeUtils.validateDayType(null, Calendar.SATURDAY));
    }

}
