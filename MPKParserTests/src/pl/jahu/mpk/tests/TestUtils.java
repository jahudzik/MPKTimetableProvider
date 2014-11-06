package pl.jahu.mpk.tests;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class TestUtils {

    public static void checkCollectionSize(Collection collection, int expectedSize) {
        assertNotNull(collection);
        assertEquals(expectedSize, collection.size());
    }

}
