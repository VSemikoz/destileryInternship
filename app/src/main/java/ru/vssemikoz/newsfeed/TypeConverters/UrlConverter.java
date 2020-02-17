package ru.vssemikoz.newsfeed.TypeConverters;

import androidx.room.TypeConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlConverter {
    @TypeConverter
    public String fromURL(URL url){
        return url.toString();
    }

    @TypeConverter
    public URL fromString(String stringUrl) {
        URL url = null;
        try {
            url  = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
