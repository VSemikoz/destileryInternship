package ru.vssemikoz.newsfeed;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.DataBase.NewsAppDataBase;
import ru.vssemikoz.newsfeed.api.NewsApi;

public class MainApplication extends Application {
    private final String MAIN_URL = "https://newsapi.org";
    private final String KEY = "c94a57cbbb50497f94a2bb167dc91fc5";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private NewsApi newsApi = retrofit.create(NewsApi.class);
    private NewsAppDataBase newsDataBase;

    public NewsApi getNewsApi() {
        return newsApi;
    }

    public String getKEY() {
        return KEY;
    }

    public NewsAppDataBase getNewsDataBase(Context context){
        if (newsDataBase == null){
            newsDataBase = Room.databaseBuilder(context, NewsAppDataBase.class, "news_data_base")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return newsDataBase;
    }
}
