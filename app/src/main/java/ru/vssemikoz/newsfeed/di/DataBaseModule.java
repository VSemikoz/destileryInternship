package ru.vssemikoz.newsfeed.di;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;

@Module
public class DataBaseModule {
    @Provides
    NewsAppDataBase provideDataBase(Context context) {
        return Room.databaseBuilder(context,
                NewsAppDataBase.class, "news_data_base")
                .fallbackToDestructiveMigration()
                .build();
    }
}
