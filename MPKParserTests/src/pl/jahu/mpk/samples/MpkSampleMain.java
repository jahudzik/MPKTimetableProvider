package pl.jahu.mpk.samples;

import pl.jahu.mpk.validators.exceptions.NoDataProvidedException;
import pl.jahu.mpk.validators.exceptions.UnsupportedLineNumberException;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class MpkSampleMain {

    public static void main(String args[]) {

//        Samples.parseFilteredLinesTimetables();
//        Samples.parseAllLines();
//        Samples.parseLineRoute();

//        Scenario1ShowLineTimetable.execute();
//        Scenario2ShowLineTransits.execute();


        try {
            TimetablesDownloader.downloadInfo();
            TimetablesDownloader.downloadTimetables(1, 1000);
        } catch (NoDataProvidedException e) {
            e.printStackTrace();
        } catch (UnsupportedLineNumberException e) {
            e.printStackTrace();
        }

    }




}
