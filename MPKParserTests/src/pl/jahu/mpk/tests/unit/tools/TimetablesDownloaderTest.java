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
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
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
    public void downloadInfoTest() throws TimetableNotFoundException {
        TimetablesDownloader.downloadInfo();
        verify(downloadUtilsMock).downloadUrl(eq(UrlResolver.LINES_LIST_URL), anyString());
        verify(downloadUtilsMock).downloadUrl(eq(UrlResolver.TIMETABLE_MENU_URL), anyString());
        verify(downloadUtilsMock).downloadUrl(eq(UrlResolver.STATIONS_LIST_URL), anyString());
    }


    @Test
    public void directionsDownloadTest() throws TimetableNotFoundException, TimetableParseException {
        when(timetableProviderMock.getLinesList()).thenReturn(buildLinesList(new int[]{1, 3, 4, 6, 7, 8, 10, 12, 15, 16, 21}));
        // there's no fifth direction, so exception should be thrown and download process finished
        doThrow(new TimetableNotFoundException()).when(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w005.htm"), anyString());

        TimetablesDownloader.downloadTimetables(16, 16);

        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w003.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w003.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w004.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w004.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0016/0016w005.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0016w005.htm"));
        verifyNoMoreInteractions(downloadUtilsMock);
    }

    @Test
    public void routeDownloadTest() throws TimetableNotFoundException, TimetableParseException {
        when(timetableProviderMock.getLinesList()).thenReturn(buildLinesList(new int[]{1, 3, 4, 6, 7, 8, 10, 12, 15, 16, 21}));
        when(timetableProviderMock.getLineRoute(new LineNumber(8), 1)).thenReturn(buildRouteStationsList());
        // there's no second direction, so exception should be thrown and download process finished
        doThrow(new TimetableNotFoundException()).when(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), anyString());

        TimetablesDownloader.downloadTimetables(8, 8);

        // download route in first direction
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008w001.htm"));
        // download stations data on the first route
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t001.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t001.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t002.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t003.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t003.htm"));
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008t004.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008t004.htm"));
        // download route in second direction - exception should be thrown
        verify(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), eq(TimetablesDownloader.TIMETABLES_LOCATION + "0008w002.htm"));
        verifyNoMoreInteractions(downloadUtilsMock);
    }

    @Test
    public void differentLinesTest() throws TimetableNotFoundException {
        when(timetableProviderMock.getLinesList()).thenReturn(buildLinesList(new int[]{1, 3, 6, 7, 8, 10, 12, 15, 16, 21}));
        // just one direction for each line
        doThrow(new TimetableNotFoundException()).when(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0003/0003w002.htm"), anyString());
        doThrow(new TimetableNotFoundException()).when(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0006/0006w002.htm"), anyString());
        doThrow(new TimetableNotFoundException()).when(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0007/0007w002.htm"), anyString());
        doThrow(new TimetableNotFoundException()).when(downloadUtilsMock).downloadUrl(eq("http://rozklady.mpk.krakow.pl/aktualne/0008/0008w002.htm"), anyString());

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
        List<StationData> stationsList = new ArrayList<StationData>();
        stationsList.add(new StationData("X1", "0008t001.htm"));
        stationsList.add(new StationData("X2", "0008t002.htm"));
        stationsList.add(new StationData("X3", "0008t003.htm"));
        stationsList.add(new StationData("X4", "0008t004.htm"));
        return stationsList;
    }


}
