package pl.jahu.mpk;

import org.joda.time.DateTimeConstants;
import org.junit.Assert;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.LineInfo;
import pl.jahu.mpk.enums.Areas;
import pl.jahu.mpk.enums.LineTypes;
import pl.jahu.mpk.enums.Vehicles;

import java.util.Collection;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class TestUtils {

    public static final DayType WEEKDAY_TYPE = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY}, false);
    public static final DayType SATURDAY_TYPE = DayType.getInstance(new int[]{DateTimeConstants.SATURDAY}, false);
    public static final DayType SUNDAY_TYPE = DayType.getInstance(new int[]{DateTimeConstants.SUNDAY}, false);
    public static final DayType EVERYDAY_NIGHT_TYPE = DayType.getInstance(new int[]{DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, DateTimeConstants.WEDNESDAY, DateTimeConstants.THURSDAY, DateTimeConstants.FRIDAY, DateTimeConstants.SATURDAY, DateTimeConstants.SUNDAY}, true);

    public static final LineInfo EXAMPLE_LINE_TYPE = new LineInfo(Vehicles.BUS, LineTypes.STANDARD, Areas.CITY);


    public static void checkCollectionSize(Collection collection, int expectedSize) {
        Assert.assertNotNull(collection);
        Assert.assertEquals(expectedSize, collection.size());
    }

}
