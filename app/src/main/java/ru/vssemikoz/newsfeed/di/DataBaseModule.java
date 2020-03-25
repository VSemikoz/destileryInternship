package ru.vssemikoz.newsfeed.di;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;

@Module
public class DataBaseModule {

    @Provides
    NewsAppDataBase provideDataBase(MainApplication application){
        return Room.databaseBuilder(application.getApplicationContext(),
                NewsAppDataBase.class, "news_data_base")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
