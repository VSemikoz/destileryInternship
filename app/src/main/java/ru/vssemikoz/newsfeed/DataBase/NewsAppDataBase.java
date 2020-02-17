package ru.vssemikoz.newsfeed.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.vssemikoz.newsfeed.DAO.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.NewsItem;

@Database(entities = {NewsItem.class}, version = 3)
public abstract class NewsAppDataBase extends RoomDatabase {
    public abstract NewsItemDAO newsItemDAO();
}
