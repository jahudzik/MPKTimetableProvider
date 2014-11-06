package pl.jahu.mpk.samples;

import pl.jahu.mpk.AppModule;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.parsers.exceptions.TimetableNotFoundException;
import pl.jahu.mpk.tools.TimetablesDownloader;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class MpkSampleMain {

    public static void main(String args[]) {

        DaggerApplication.init(new AppModule());

        try {
            TimetablesDownloader.downloadInfo();
            TimetablesDownloader.downloadTimetables(1, 1000);
        } catch (TimetableNotFoundException e) {
            e.printStackTrace();
        }

    }

}
