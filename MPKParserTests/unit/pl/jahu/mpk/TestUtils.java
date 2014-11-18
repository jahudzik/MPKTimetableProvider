package pl.jahu.mpk;

import org.joda.time.DateTimeConstants;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.LineType;
import pl.jahu.mpk.enums.AreaTypes;
import pl.jahu.mpk.enums.ReasonTypes;
import pl.jahu.mpk.enums.VehicleTypes;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class TestUtils {

    public static final DayType WEEKDAY_TYPE = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY}, false);
    public static final DayType SATURDAY_TYPE = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY}, false);
    public static final DayType SUNDAY_TYPE = DayType.getInstance(new int[]{DateTimeConstants.SUNDAY}, false);
    public static final DayType EVERYDAY_NIGHT_TYPE = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, true);

    public static final LineType EXAMPLE_LINE_TYPE = new LineType(VehicleTypes.BUS, ReasonTypes.STANDARD, AreaTypes.CITY);


    public static void checkCollectionSize(Collection collection, int expectedSize) {
        assertNotNull(collection);
        assertEquals(expectedSize, collection.size());
    }

}
