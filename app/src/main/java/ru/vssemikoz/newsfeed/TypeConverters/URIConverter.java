package ru.vssemikoz.newsfeed.TypeConverters;

import android.util.Log;

import androidx.room.TypeConverter;

import java.net.URI;
import java.net.URISyntaxException;

public class URIConverter {
    @TypeConverter
    public String fromURL(URI url){
        Log.d("MyLog", "fromURL " + url.toString());
        return url.toString();
    }

    @TypeConverter
    public URI fromString(String stringUrl) {
        URI url;
        try {
            url  = new URI(stringUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d("MyLog", "fromString Null" );
            return null;
        }
        Log.d("MyLog", "fromString " + url);
        return url;
    }
}
