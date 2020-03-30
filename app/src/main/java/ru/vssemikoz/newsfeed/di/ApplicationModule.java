package ru.vssemikoz.newsfeed.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.MainApplication;

@Module
public class ApplicationModule {
    private MainApplication application;

    public ApplicationModule(MainApplication application) {
        this.application = application;
    }

    @Provides
    MainApplication provideApplication() {
        return application;
    }

    @Provides
    Context provideContext() {
        return application.getApplicationContext();
    }
}
