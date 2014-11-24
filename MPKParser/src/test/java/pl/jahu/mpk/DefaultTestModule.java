package pl.jahu.mpk;

import dagger.Module;
import dagger.Provides;
import pl.jahu.mpk.providers.FileTimetableProvider;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.integration.ShowTimetableIntegrationTest;
import pl.jahu.mpk.parsers.LineRouteParserTest;
import pl.jahu.mpk.parsers.LinesListParserTest;
import pl.jahu.mpk.parsers.TimetableParserTest;

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

    private static final String TEST_FILES_LOCATION = "MPKParser/src/test/resources/";
    private static final String DEFAULT_TEST_FILES_DIRECTORY = "default";

    private String filesLocation;


    public DefaultTestModule() {
        this(DEFAULT_TEST_FILES_DIRECTORY);
    }

    public DefaultTestModule(String filesDirectory) {
        this.filesLocation = TEST_FILES_LOCATION + filesDirectory;
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    TimetableProvider provideTimetableProvider() {
        return new FileTimetableProvider(filesLocation);
    }

}
