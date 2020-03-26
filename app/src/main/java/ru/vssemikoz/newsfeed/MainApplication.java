package ru.vssemikoz.newsfeed;

import android.app.Application;
import ru.vssemikoz.newsfeed.di.ApplicationComponent;
import ru.vssemikoz.newsfeed.di.ApplicationModule;
import ru.vssemikoz.newsfeed.di.DaggerApplicationComponent;
import ru.vssemikoz.newsfeed.di.DataBaseModule;
import ru.vssemikoz.newsfeed.di.NetworkModule;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getName();
    private static ApplicationComponent applicationComponent;
    // TODO: 26.03.2020 into gradle build
    private final String MAIN_URL = "https://newsapi.org";

    private static MainApplication instance;

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
    }

}
