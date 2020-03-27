package ru.vssemikoz.newsfeed;

import android.app.Application;

import ru.vssemikoz.newsfeed.di.ApplicationComponent;
import ru.vssemikoz.newsfeed.di.ApplicationModule;
import ru.vssemikoz.newsfeed.di.DaggerApplicationComponent;

public class MainApplication extends Application {
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
                .build();
    }

}
