package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.LocalNewsStorage;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;

@Module
public class StorageModule {
    @Provides
    LocalNewsStorage provideStorage(NewsAppDataBase dataBase){
        return new LocalNewsStorage(dataBase);
    }
}
