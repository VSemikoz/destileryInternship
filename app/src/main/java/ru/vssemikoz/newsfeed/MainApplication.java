package ru.vssemikoz.newsfeed;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import androidx.room.Room;

import com.mikepenz.iconics.IconicsColor;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.IconicsSize;
import com.mikepenz.iconics.typeface.library.googlematerial.GoogleMaterial;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.DataBase.NewsAppDataBase;
import ru.vssemikoz.newsfeed.api.NewsApi;

public class MainApplication extends Application {
    private final String MAIN_URL = "https://newsapi.org";
    private final String KEY = "c94a57cbbb50497f94a2bb167dc91fc5";

    private static Context mContext;
    private Retrofit retrofit;
    private NewsApi newsApi;
    private NewsAppDataBase newsDataBase;

    private Drawable whiteStarWithoutBorders;
    private Drawable yellowStarWithoutBorders;
    private Drawable yellowStarWithBorders;
    private Drawable whiteStarWithBorders;

    public Drawable getWhiteStarWithoutBorders() {
        return whiteStarWithoutBorders;
    }

    public Drawable getYellowStarWithoutBorders() {
        return yellowStarWithoutBorders;
    }

    public Drawable getYellowStarWithBorders() {
        return yellowStarWithBorders;
    }

    public Drawable getWhiteStarWithBorders() {
        return whiteStarWithBorders;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initDrawableRes();
        initRetrofit();
        initNewsApi();
        initNewsDataBase(this);
    }

    void initDrawableRes() {
        whiteStarWithoutBorders = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_star)
                .color(IconicsColor.colorInt(Color.WHITE))
                .size(IconicsSize.dp(48));

        yellowStarWithoutBorders = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_star)
                .color(IconicsColor.colorInt(Color.YELLOW))
                .size(IconicsSize.dp(48));

        whiteStarWithBorders = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_star)
                .color(IconicsColor.colorInt(Color.WHITE))
                .size(IconicsSize.dp(48))
                .contourColor(IconicsColor.colorInt(Color.BLACK))
                .contourWidth(IconicsSize.dp(2));

        yellowStarWithBorders = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_star)
                .color(IconicsColor.colorInt(Color.YELLOW))
                .size(IconicsSize.dp(48))
                .contourColor(IconicsColor.colorInt(Color.BLACK))
                .contourWidth(IconicsSize.dp(2));
    }

    void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    void initNewsApi() {
        newsApi = retrofit.create(NewsApi.class);
    }

    void initNewsDataBase(Context context) {
        newsDataBase = Room.databaseBuilder(context, NewsAppDataBase.class, "news_data_base")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public NewsApi getNewsApi() {
        return newsApi;
    }

    public String getKEY() {
        return KEY;
    }

    public NewsAppDataBase getNewsDataBase() {
        return newsDataBase;
    }
}
