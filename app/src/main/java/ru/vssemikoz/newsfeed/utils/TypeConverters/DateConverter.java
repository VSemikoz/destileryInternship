package ru.vssemikoz.newsfeed.utils.TypeConverters;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    final static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

    @TypeConverter
    public static String fromDate(Date date){
        return new SimpleDateFormat(dateFormat).format(date);
    }

    @TypeConverter
    public static Date fromString(String stringDate){
        Date date = null;
        try {
            date = new SimpleDateFormat(dateFormat).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
