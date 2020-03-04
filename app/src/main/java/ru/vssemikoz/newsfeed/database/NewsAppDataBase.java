package ru.vssemikoz.newsfeed.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.NewsItem;

@Database(entities = {NewsItem.class}, version = 1, exportSchema = false)
public abstract class NewsAppDataBase extends RoomDatabase {
    public abstract NewsItemDAO newsItemDAO();
}
