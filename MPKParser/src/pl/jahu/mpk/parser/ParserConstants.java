package pl.jahu.mpk.parser;

import pl.jahu.mpk.enums.DayTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jahudzik on 2014-07-13.
 */
public class ParserConstants {

    public static final String WEEKDAY_DAY_TYPE_LABEL = "Dzień powszedni";
    public static final String SATURDAY_DAY_TYPE_LABEL = "Soboty";
    public static final String SUNDAY_DAY_TYPE_LABEL = "Święta";
    public static final String EVERYDAY_DAY_TYPE_LABEL = "Wszystkie dni tygodnia";
    public static final String MONDAY_TO_THURSDAY_DAY_TYPE_LABEL = "Pon.-Czw.";
    public static final String WEEKEND_NIGHTS_DAY_TYPE_LABEL = "Pt/Sob.,Sob./Św.";

    public static final Map<String, DayTypes> dayTypesNames = new HashMap<String, DayTypes>();
    static {
        dayTypesNames.put(WEEKDAY_DAY_TYPE_LABEL, DayTypes.WEEKDAY);
        dayTypesNames.put(SATURDAY_DAY_TYPE_LABEL, DayTypes.SATURDAY);
        dayTypesNames.put(SUNDAY_DAY_TYPE_LABEL, DayTypes.SUNDAY);
        dayTypesNames.put(EVERYDAY_DAY_TYPE_LABEL, DayTypes.EVERYDAY);
        dayTypesNames.put(MONDAY_TO_THURSDAY_DAY_TYPE_LABEL, DayTypes.MONDAY_TO_THURSDAY);
        dayTypesNames.put(WEEKEND_NIGHTS_DAY_TYPE_LABEL, DayTypes.WEEKEND_NIGHTS);
    }

}
