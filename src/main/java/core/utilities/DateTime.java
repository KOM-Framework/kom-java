package core.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by olehk on 05/05/2017.
 */
public class DateTime {

    public enum CalendarType {
        DAY, MONTH, YEAR, HOUR, MINUTE
    }

    private static int getCalendarType(CalendarType type) {
        int calendarType = 0;
        switch (type) {
            case DAY:
                calendarType = Calendar.DAY_OF_YEAR;
            case MONTH:
                calendarType = Calendar.MONTH;
            case YEAR:
                calendarType = Calendar.YEAR;
            case HOUR:
                calendarType = Calendar.HOUR;
            case MINUTE:
                calendarType = Calendar.MINUTE;
        }
        return calendarType;
    }

    public static boolean isDateFormat(String stringToValidate, String expectedDateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(expectedDateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(stringToValidate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static String getDateFromNow(CalendarType type, int numberOfUnitsToAdd, String dateFormat){
        Calendar calendar = Calendar.getInstance();
        DateFormat currentDate = new SimpleDateFormat(dateFormat);
        calendar.add(getCalendarType(type), numberOfUnitsToAdd);
        Date date = calendar.getTime();
        return currentDate.format(date);
    }

    public static String getDateFromNow(CalendarType type, int numberOfUnitsToAdd){
        return getDateFromNow(type, numberOfUnitsToAdd, "dd/MM/yyyy");
    }

    public static String getYearFromDateNow(int numberOfYearToAdd, String dateFormat) {
        return getDateFromNow(CalendarType.YEAR, numberOfYearToAdd, dateFormat);
    }

    public static String getMonthFromDateNow(int numberOfMonthToAdd, String dateFormat) {
        return getDateFromNow(CalendarType.MONTH, numberOfMonthToAdd, dateFormat);
    }

    public static String getSTNTime() {
        Calendar now = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.set(2001, Calendar.JANUARY, 1, 0, 0, 0);
        return String.valueOf((now.getTimeInMillis() - start.getTimeInMillis()) / 1000);
    }

}
