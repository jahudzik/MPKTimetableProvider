package pl.jahu.mpk;

import dagger.Module;
import dagger.Provides;
import pl.jahu.mpk.providers.FileTimetableProvider;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tests.integration.ShowTimetableIntegrationTest;
import pl.jahu.mpk.tests.unit.parsers.LineRouteParserTest;
import pl.jahu.mpk.tests.unit.parsers.LinesListParserTest;
import pl.jahu.mpk.tests.unit.parsers.TimetableParserTest;

import javax.inject.Singleton;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
@Module(
        staticInjections = {
                ShowTimetableIntegrationTest.class
        },
        injects = {
                FileTimetableProvider.class,
                LinesListParserTest.class,
                LineRouteParserTest.class,
                TimetableParserTest.class
        },
        overrides = true,
        includes = AppModule.class
)
public class DefaultTestModule {

    public static final String FILES_LOCATION = "MPKParserTests/res";

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    TimetableProvider provideTimetableProvider() {
        return new FileTimetableProvider(FILES_LOCATION);
    }

}
