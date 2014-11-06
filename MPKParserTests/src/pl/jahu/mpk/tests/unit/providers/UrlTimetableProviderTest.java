package pl.jahu.mpk.tests.unit.providers;

import dagger.Module;
import dagger.Provides;
import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.AppModule;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.providers.UrlTimetableProvider;
import pl.jahu.mpk.utils.DownloadUtils;
import pl.jahu.mpk.utils.UrlResolver;

import javax.inject.Singleton;
import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class UrlTimetableProviderTest {

    private LinesListParser linesListParserMock;

    private LineRouteParser lineRouteParserMock;

    private TimetableParser timetableParserMock;

    private DownloadUtils downloadUtilsMock;

    private ParsableData parsableDataMock;

    private TimetableProvider timetableProvider;

    @Module(
            injects = {
                    UrlTimetableProvider.class
            },
            overrides = true,
            includes = AppModule.class
    )
    public class UrlTimetableProviderTestModule {

        @Provides
        @Singleton
        @SuppressWarnings("unused")
        LinesListParser provideLinesListParser() {
            linesListParserMock = mock(LinesListParser.class);
            return linesListParserMock;
        }

        @Provides
        @Singleton
        @SuppressWarnings("unused")
        LineRouteParser provideLineRouteParser() {
            lineRouteParserMock = mock(LineRouteParser.class);
            return lineRouteParserMock;
        }

        @Provides
        @Singleton
        @SuppressWarnings("unused")
        TimetableParser provideTimetableParser() {
            timetableParserMock = mock(TimetableParser.class);
            return timetableParserMock;
        }

        @Provides
        @Singleton
        @SuppressWarnings("unused")
        public DownloadUtils provideDownloadUtils() {
            downloadUtilsMock = mock(DownloadUtils.class);
            return downloadUtilsMock;
        }
    }

    @Before
    public void setUp() throws IOException {
        DaggerApplication.init(new UrlTimetableProviderTestModule());
        timetableProvider = new UrlTimetableProvider();
        parsableDataMock = mock(ParsableData.class);
        when(downloadUtilsMock.downloadJsoupDocument(anyString())).thenReturn(parsableDataMock);
    }

    @Test
    public void getLinesListTest() throws TimetableNotFoundException, IOException {
        timetableProvider.getLinesList();
        verify(downloadUtilsMock).downloadJsoupDocument(eq(UrlResolver.LINES_LIST_URL));
        verify(linesListParserMock).parse(parsableDataMock);
    }

    @Test
    public void getLineRouteTest() throws TimetableNotFoundException, TimetableParseException, IOException {
        LineNumber lineNumber = new LineNumber(5);
        timetableProvider.getLineRoute(lineNumber, 1);
        verify(downloadUtilsMock).downloadJsoupDocument(eq("http://rozklady.mpk.krakow.pl/aktualne/0005/0005w001.htm"));
        verify(lineRouteParserMock).parse(parsableDataMock);
    }

    @Test
    public void getTimetableTest() throws TimetableParseException, TimetableNotFoundException, IOException {
        LineNumber lineNumber = new LineNumber(605);
        timetableProvider.getTimetable(lineNumber, "0605t0017.htm");
        verify(downloadUtilsMock).downloadJsoupDocument(eq("http://rozklady.mpk.krakow.pl/aktualne/0605/0605t0017.htm"));
        verify(timetableParserMock).parse(parsableDataMock, lineNumber);
    }

}
