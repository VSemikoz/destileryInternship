package ru.vssemikoz.newsfeed.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

import ru.vssemikoz.newsfeed.models.NewsItem;

@Dao
public interface NewsItemDAO {

    @Query("SELECT * FROM NewsItem")
    List<NewsItem> getAll();

    @Query("SELECT * FROM NewsItem WHERE published_at < :first AND published_at > :second")
    List<NewsItem> getNewsByDate(Date first, Date second);

    @Insert
    void insertAll(NewsItem newsItems);

    @Delete
    void delete(NewsItem newsItem);
}
