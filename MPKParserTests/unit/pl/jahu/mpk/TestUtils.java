package pl.jahu.mpk;

import pl.jahu.mpk.entities.DayType;

import java.util.Calendar;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class TestUtils {

    public static final DayType WEEKDAY_TYPE = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY}, false);
    public static final DayType SATURDAY_TYPE = DayType.getInstance(new int[]{Calendar.SATURDAY}, false);
    public static final DayType SUNDAY_TYPE = DayType.getInstance(new int[]{Calendar.SUNDAY}, false);
    public static final DayType SUNDAY_NIGHT_TYPE = DayType.getInstance(new int[]{Calendar.SUNDAY}, true);
    public static final DayType EVERYDAY_NIGHT_TYPE = DayType.getInstance(new int[]{Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY}, true);


    public static void checkCollectionSize(Collection collection, int expectedSize) {
        assertNotNull(collection);
        assertEquals(expectedSize, collection.size());
    }

}
