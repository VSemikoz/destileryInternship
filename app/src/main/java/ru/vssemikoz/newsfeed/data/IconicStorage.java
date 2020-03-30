package ru.vssemikoz.newsfeed.data;

import android.content.Context;
import android.graphics.drawable.Drawable;


public interface IconicStorage {
    Drawable getYellowStarBorderless(Context context);

    Drawable getYellowStarBorder(Context context);

    Drawable getWhiteStarBorderless(Context context);

    Drawable getWhiteStarBorder(Context context);

}
