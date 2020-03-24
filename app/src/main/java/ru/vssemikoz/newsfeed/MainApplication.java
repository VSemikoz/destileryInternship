package ru.vssemikoz.newsfeed;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.di.NewsFeedFragmentComponent;

public class MainApplication extends Application {
    private static NewsFeedFragmentComponent component;
    private final String MAIN_URL = "https://newsapi.org";
    private final String KEY = "c94a57cbbb50497f94a2bb167dc91fc5";

    private static MainApplication instance;
    private Retrofit retrofit;
    private NewsApi newsApi;
    private NewsAppDataBase newsDataBase;

    public static MainApplication getInstance() {
        return instance;
    }

    public static NewsFeedFragmentComponent getComponent(){
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 24.03.2020 don't know how provide in  NewsFeedPresenterModule(...) existing fragment
//        component = DaggerNewsFeedFragmentComponent.builder()
//                .newsFeedPresenterModule(new NewsFeedPresenterModule(new NewsFeedFragment()))
//                .build();

        instance = this;
        initRetrofit();
        initNewsApi();
        initNewsDatabase(this);
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

    void initNewsDatabase(Context context) {
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
