package pl.jahu.mpk;

import dagger.Module;
import dagger.Provides;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.providers.*;
import pl.jahu.mpk.tools.TimetablesDownloader;
import pl.jahu.mpk.tools.TransitBuilder;
import pl.jahu.mpk.utils.DownloadUtils;

import javax.inject.Singleton;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
@Module(
        injects = {
                TimetableProvider.class,
                UrlTimetableProvider.class
        },
        staticInjections = {
                TimetablesDownloader.class,
                TransitBuilder.class
        }
)
public class AppModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    LinesListParser provideLinesListParser() {
        return new LinesListParser();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    LineRouteParser provideLineRouteParser() {
        return new LineRouteParser();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    TimetableParser provideTimetableParser() {
        return new TimetableParser();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    TimetableProvider provideTimetableProvider() {
        return new UrlTimetableProvider();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    DownloadUtils provideDownloadUtils() {
        return new DownloadUtils();
    }

}
