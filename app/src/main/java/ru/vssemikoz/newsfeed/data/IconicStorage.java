package ru.vssemikoz.newsfeed.data;

import android.content.Context;
import android.graphics.drawable.Drawable;

public interface IconicStorage {

    Drawable getFilteredDrawable(Context context);

    Drawable getUnfilteredDrawable(Context context);

    Drawable getAddIntoFilteredDrawable(Context context);

    Drawable getRemoveFromFilteredDrawable(Context context);
}
