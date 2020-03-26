package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;

@Module
public class StorageModule {
    @Provides
    NewsStorage provideStorage(NewsAppDataBase dataBase){
        return new NewsStorage(dataBase);
    }
}
