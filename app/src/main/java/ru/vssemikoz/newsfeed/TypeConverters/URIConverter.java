package ru.vssemikoz.newsfeed.TypeConverters;

import androidx.room.TypeConverter;

import java.net.URI;
import java.net.URISyntaxException;

public class URIConverter {
    @TypeConverter
    public String fromURL(URI url){
        return (url != null) ? url.toString() : "";
    }

    @TypeConverter
    public URI fromString(String stringUrl) {
        URI url;
        try {
            url  = new URI(stringUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }
}
