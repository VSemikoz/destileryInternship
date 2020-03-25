package ru.vssemikoz.newsfeed;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;
import ru.vssemikoz.newsfeed.di.ApplicationComponent;
import ru.vssemikoz.newsfeed.di.ApplicationModule;
import ru.vssemikoz.newsfeed.di.DaggerApplicationComponent;
import ru.vssemikoz.newsfeed.di.DataBaseModule;
import ru.vssemikoz.newsfeed.di.NetworkModule;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getName();
    private static ApplicationComponent applicationComponent;
    private final String MAIN_URL = "https://newsapi.org";
    private final String KEY = "c94a57cbbb50497f94a2bb167dc91fc5";

    private static MainApplication instance;
    @Inject
    Retrofit retrofit;
    @Inject
    NewsApi newsApi;
    @Inject
    NewsAppDataBase newsDataBase;

    public static MainApplication getInstance() {
        return instance;
    }

    public static ApplicationComponent getApplicationComponent(){
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instance))
                .networkModule(new NetworkModule(MAIN_URL))
                .dataBaseModule(new DataBaseModule())
                .build();
        applicationComponent.inject(instance);
        Log.d(TAG, "onCreate: " + retrofit);

//        initRetrofit();
//        initNewsApi();
//        initNewsDatabase(this);
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
