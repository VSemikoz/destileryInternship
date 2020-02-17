package ru.vssemikoz.newsfeed.TypeConverters;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    @TypeConverter
    public String fromDate(Date date){
        return date.toString();
    }

    @TypeConverter
    public Date fromString(String stringDate){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
