package pl.jahu.mpk.providers;

import dagger.Module;
import dagger.Provides;
import org.junit.Before;
import org.junit.Test;
import pl.jahu.mpk.AppModule;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.TestUtils;
import pl.jahu.mpk.entities.DayType;
import pl.jahu.mpk.entities.Line;
import pl.jahu.mpk.entities.LineInfo;
import pl.jahu.mpk.enums.Areas;
import pl.jahu.mpk.enums.LineTypes;
import pl.jahu.mpk.enums.Vehicles;
import pl.jahu.mpk.parsers.LineRouteParser;
import pl.jahu.mpk.parsers.LinesListParser;
import pl.jahu.mpk.parsers.TimetableParser;
import pl.jahu.mpk.parsers.data.ParsableData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.utils.DownloadUtils;
import pl.jahu.mpk.utils.TimeUtils;
import pl.jahu.mpk.utils.UrlResolver;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    public void getLastUpdateDate_test() throws TimetableParseException, ParsableDataNotFoundException, IOException {
        timetableProvider.getLastUpdateDate();

        verify(downloadUtilsMock).downloadJsoupDocument(eq(UrlResolver.UPDATE_INFO_URL));
        verify(linesListParserMock).parseLastUpdateDate(parsableDataMock);
    }

    @Test
    public void getLinesList_test() throws ParsableDataNotFoundException, IOException {
        timetableProvider.getLinesList();

        verify(downloadUtilsMock).downloadJsoupDocument(eq(UrlResolver.LINES_LIST_URL));
        verify(linesListParserMock).parseAll(parsableDataMock);
    }

    @Test
    public void getChangedLinesList_test() throws ParsableDataNotFoundException, IOException {
        timetableProvider.getChangedLinesList();

        verify(downloadUtilsMock).downloadJsoupDocument(eq(UrlResolver.LINES_LIST_URL));
        verify(linesListParserMock).parseChanged(parsableDataMock);
    }

    @Test
    public void getLineRoute_test() throws ParsableDataNotFoundException, TimetableParseException, IOException {
        Line line = new Line(5, TestUtils.EXAMPLE_LINE_TYPE);

        timetableProvider.getLineRoute(line, 1);

        verify(downloadUtilsMock).downloadJsoupDocument(eq("http://rozklady.mpk.krakow.pl/aktualne/0005/0005w001.htm"));
        verify(lineRouteParserMock).parse(line, parsableDataMock);
    }

    @Test
    public void getTimetable_test() throws TimetableParseException, ParsableDataNotFoundException, IOException {
        Line line = new Line(605, new LineInfo(Vehicles.BUS, LineTypes.NIGHTLY, Areas.CITY));
        List<DayType> dayTypeList = Arrays.asList(TestUtils.WEEKDAY_TYPE, TestUtils.SATURDAY_TYPE, TestUtils.SUNDAY_TYPE);
        when(timetableParserMock.parseDayTypes(any(ParsableData.class), anyBoolean())).thenReturn(dayTypeList);

        timetableProvider.getTimetable(TimeUtils.buildDate(14, 11, 2014), line, 17);

        // should download specific timetable
        verify(downloadUtilsMock).downloadJsoupDocument(eq("http://rozklady.mpk.krakow.pl/aktualne/0605/0605t017.htm"));
        // should parse day types and return dayTypeList (defined above)
        verify(timetableParserMock).parseDayTypes(eq(parsableDataMock), eq(true));
        // should match passed date (Thursday) with first element (WEEKDAY_TYPE) on dayTypeList and pass its index to parse() method
        verify(timetableParserMock).parseDepartures(eq(parsableDataMock), eq(dayTypeList), eq(0), eq(line));
    }

}
