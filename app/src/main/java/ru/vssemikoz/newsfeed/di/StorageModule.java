package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.data.NewsStorage;

@Module
public class StorageModule {
    @Provides
    NewsStorage provideStorage(MainApplication application){
        return new NewsStorage(application);
    }
}
