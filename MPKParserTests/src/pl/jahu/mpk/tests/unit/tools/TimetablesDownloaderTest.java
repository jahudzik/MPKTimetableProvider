package pl.jahu.mpk.tests.unit.tools;

import dagger.Module;
import dagger.Provides;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import pl.jahu.mpk.AppModule;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.entities.LineNumber;
import pl.jahu.mpk.parsers.data.StationData;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.parsers.exceptions.TimetableParseException;
import pl.jahu.mpk.providers.TimetableProvider;
import pl.jahu.mpk.tools.TimetablesDownloader;
import pl.jahu.mpk.utils.DownloadUtils;
import pl.jahu.mpk.utils.UrlResolver;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-11-04.
 */
public class TimetablesDownloaderTest {

    private TimetableProvider timetableProviderMock;

    private DownloadUtils downloadUtilsMock;

    @Module(
            staticInjections = {
                    TimetablesDownloader.class
            },
            overrides = true,
            includes = AppModule.class
    )
    public class TimetablesDownloaderTestModule {

        @Provides
        @Singleton
        @SuppressWarnings("unused")
        public TimetableProvider provideTimetableProvider() {
            timetableProviderMock = Mockito.mock(TimetableProvider.class);
            return timetableProviderMock;
        }

        @Provides
        @Singleton
        @SuppressWarnings("unused")
        public DownloadUtils provideDownloadUtils() {
            downloadUtilsMock = Mockito.mock(DownloadUtils.class);
            return downloadUtilsMock;
        }

    }

    @Before
    public void setUp() {
        DaggerApplication.init(new TimetablesDownloaderTestModule());
    }


    /******************** TESTS ********************/

    @Test
    public void downloadInfoTest() throws ParsableDataNotFoundException {
        TimetablesDownloader.downloadInfo();
        verify(downloadUtilsMock).downloadUrl(eq(UrlResolver.LINES_LIST_URL), anyString());
        verify(downloadUtilsMock).downloadUrl(eq(UrlResolver.TIMETABLE_MENU_URL), anyString());
        verify(downloadUtilsMock).downloadUrl(eq(UrlResolver.STATIONS_LIST_URL), anyString());
    }


    @Test
    public void directionsDownloadTest() throws ParsableDataNotFoundException, TimetableParseException {
        when(timetableProviderMock.getLinesList()).thenReturn(buildLinesList(new int[]{1, 3, 4, 6, 7, 8, 10, 12, 15, 16, 21}));
        // four directions
        when(downloadUtilsMock.downloadUrl(anyString(), anyString())).thenReturn(true);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w005.htm"), anyString())).thenReturn(false);

        TimetablesDownloader.downloadTimetables(16, 16);

        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w003.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w003.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w004.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w004.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w005.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w005.htm"));
        verifyNoMoreInteractions(downloadUtilsMock);
    }

    @Test
    public void routeDownloadTest() throws ParsableDataNotFoundException, TimetableParseException {
        when(timetableProviderMock.getLinesList()).thenReturn(buildLinesList(new int[]{1, 3, 4, 6, 7, 8, 10, 12, 15, 16, 21}));
        when(timetableProviderMock.getLineRoute(new LineNumber(8), 1)).thenReturn(buildRouteStationsList());
        // just one direction
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w001.htm"), anyString())).thenReturn(true);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), anyString())).thenReturn(false);

        TimetablesDownloader.downloadTimetables(8, 8);

        // download route in first direction
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008w001.htm"));
        // download stations data on the first route
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t003.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t003.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t004.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t004.htm"));
        // there's no second direction - it should be the last invocation
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008w002.htm"));
        verifyNoMoreInteractions(downloadUtilsMock);
    }

    @Test
    public void differentLinesTest() throws ParsableDataNotFoundException {
        when(timetableProviderMock.getLinesList()).thenReturn(buildLinesList(new int[]{1, 3, 6, 7, 8, 10, 12, 15, 16, 21}));
        // just one direction for each line
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0003/0003w001.htm"), anyString())).thenReturn(true);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0003/0003w002.htm"), anyString())).thenReturn(false);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0006/0006w001.htm"), anyString())).thenReturn(true);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0006/0006w002.htm"), anyString())).thenReturn(false);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0007/0007w001.htm"), anyString())).thenReturn(true);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0007/0007w002.htm"), anyString())).thenReturn(false);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w001.htm"), anyString())).thenReturn(true);
        when(downloadUtilsMock.downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), anyString())).thenReturn(false);

        TimetablesDownloader.downloadTimetables(2, 8);

        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0003/0003w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0003w001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0003/0003w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0003w002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0006/0006w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0006w001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0006/0006w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0006w002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0007/0007w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0007w001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0007/0007w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0007w002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008w001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008w002.htm"));
        verifyNoMoreInteractions(downloadUtilsMock);
    }


    /******************** API ********************/

    private List<LineNumber> buildLinesList(int[] numbers) {
        List<LineNumber> linesList = new ArrayList<LineNumber>();
        for (int number : numbers) {
            linesList.add(new LineNumber(number));
        }
        return linesList;
    }

    private List<StationData> buildRouteStationsList() {
        LineNumber lineNumber = new LineNumber(8);
        List<StationData> stationsList = new ArrayList<StationData>();
        stationsList.add(new StationData("X1", lineNumber, 1));
        stationsList.add(new StationData("X2", lineNumber, 2));
        stationsList.add(new StationData("X3", lineNumber, 3));
        stationsList.add(new StationData("X4", lineNumber, 4));
        return stationsList;
    }


}
