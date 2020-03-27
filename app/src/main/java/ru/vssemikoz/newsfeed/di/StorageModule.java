package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.LocalNewsStorage;
import ru.vssemikoz.newsfeed.data.NewsStorage;

@Module
public class StorageModule {
    @Provides
    NewsStorage provideStorage(LocalNewsStorage newsStorage){
        return newsStorage;
    }
}
