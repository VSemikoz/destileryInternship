package ru.vssemikoz.newsfeed.utils.TypeConverters;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    private final static String DATABASE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final static String UI_FORMAT = "dd MMM yyyy HH:mm:ss";//Mon, 13 Apr 2015 22:59:26

    @TypeConverter
    public static String fromDate(Date date){
        return new SimpleDateFormat(DATABASE_FORMAT).format(date);
    }

    @TypeConverter
    public static Date fromString(String stringDate){
        Date date = null;
        try {
            date = new SimpleDateFormat(DATABASE_FORMAT).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String fromDateToHumanReadable(Date date){
        return new SimpleDateFormat(UI_FORMAT).format(date);
    }
}
