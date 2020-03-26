package ru.vssemikoz.newsfeed.data;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.mikepenz.iconics.IconicsColor;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.IconicsSize;
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial;

public class NewsIconicStorage implements IconicStorage {
    private static Drawable STAR_YELLOW_BORDERLESS = null;
    private static Drawable STAR_YELLOW_BORDER = null;
    private static Drawable STAR_WHITE_BORDERLESS = null;
    private static Drawable STAR_WHITE_BORDER = null;

    public static Drawable getYellowStarBorderless(Context context) {
        if (STAR_YELLOW_BORDERLESS == null) {
            STAR_YELLOW_BORDERLESS = new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_star)
                    .color(IconicsColor.colorInt(Color.YELLOW));
        }
        return STAR_YELLOW_BORDERLESS;
    }

    public static Drawable getYellowStarBorder(Context context) {
        if (STAR_YELLOW_BORDER == null) {
            STAR_YELLOW_BORDER = new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_star)
                    .color(IconicsColor.colorInt(Color.YELLOW))
                    .contourColor(IconicsColor.colorInt(Color.BLACK))
                    .contourWidth(IconicsSize.dp(2));
        }
        return STAR_YELLOW_BORDER;
    }

    public static Drawable getWhiteStarBorderless(Context context) {
        if (STAR_WHITE_BORDERLESS == null) {
            STAR_WHITE_BORDERLESS = new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_star)
                    .color(IconicsColor.colorInt(Color.WHITE));
        }
        return STAR_WHITE_BORDERLESS;
    }

    public static Drawable getWhiteStarBorder(Context context) {
        if (STAR_WHITE_BORDER == null) {
            STAR_WHITE_BORDER = new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_star)
                    .color(IconicsColor.colorInt(Color.WHITE))
                    .contourColor(IconicsColor.colorInt(Color.BLACK))
                    .contourWidth(IconicsSize.dp(2));
        }
        return STAR_WHITE_BORDER;
    }

    // TODO: 26.03.2020 How to make override methods static to use without object?
    @Override
    public Drawable getFilteredDrawable(Context context) {
        return getWhiteStarBorderless(context);
    }

    @Override
    public Drawable getUnfilteredDrawable(Context context) {
        return getYellowStarBorderless(context);
    }

    @Override
    public Drawable getAddIntoFilteredDrawable(Context context) {
        return getWhiteStarBorder(context);
    }

    @Override
    public Drawable getRemoveFromFilteredDrawable(Context context) {
        return getYellowStarBorder(context);
    }
}
