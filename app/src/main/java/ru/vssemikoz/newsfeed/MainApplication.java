package ru.vssemikoz.newsfeed;

import android.app.Application;

import ru.vssemikoz.newsfeed.di.AppConfigModule;
import ru.vssemikoz.newsfeed.di.ApplicationComponent;
import ru.vssemikoz.newsfeed.di.ApplicationModule;
import ru.vssemikoz.newsfeed.di.DaggerApplicationComponent;
import ru.vssemikoz.newsfeed.di.DataBaseModule;
import ru.vssemikoz.newsfeed.di.NetworkModule;

public class MainApplication extends Application {
    private static final String TAG = MainApplication.class.getName();
    private static ApplicationComponent applicationComponent;

    private static MainApplication instance;

    public static MainApplication getInstance() {
        return instance;
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instance))
                .networkModule(new NetworkModule())
                .dataBaseModule(new DataBaseModule())
                .appConfigModule(new AppConfigModule())
                .build();
    }

}
