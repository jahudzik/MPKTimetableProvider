package pl.jahu.mpk.samples;

import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.tools.TimetablesDownloader;
import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class MpkSampleMain {

    public static void main(String args[]) {

        DaggerApplication.init();

        try {
            TimetablesDownloader.downloadInfo();
            TimetablesDownloader.downloadTimetables(1, 1000);
        } catch (NoDataProvidedException e) {
            e.printStackTrace();
        } catch (UnsupportedLineNumberException e) {
            e.printStackTrace();
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        }

    }

}
