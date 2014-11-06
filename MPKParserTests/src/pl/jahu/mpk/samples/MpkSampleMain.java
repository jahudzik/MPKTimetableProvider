package pl.jahu.mpk.samples;

import pl.jahu.mpk.AppModule;
import pl.jahu.mpk.DaggerApplication;
import pl.jahu.mpk.parsers.exceptions.ParsableDataNotFoundException;
import pl.jahu.mpk.tools.TimetablesDownloader;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class MpkSampleMain {

    public static void main(String args[]) {

        DaggerApplication.init(new AppModule());

        try {
            TimetablesDownloader.downloadInfo();
            TimetablesDownloader.downloadTimetables(1, 3);
        } catch (ParsableDataNotFoundException e) {
            e.printStackTrace();
        }

    }

}
