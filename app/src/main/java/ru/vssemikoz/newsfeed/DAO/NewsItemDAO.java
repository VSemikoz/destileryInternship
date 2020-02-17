package ru.vssemikoz.newsfeed.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.vssemikoz.newsfeed.models.NewsItem;

@Dao
public interface NewsItemDAO {

    @Query("SELECT * FROM NewsItem")
    List<NewsItem> getAll();

    @Query("SELECT * FROM NewsItem WHERE published_at < :first AND published_at > :second")
    List<NewsItem> getNewsByDate(String first, String second);

    @Insert
    void insertAll(List<NewsItem> newsItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUnique(List<NewsItem> newsItems);

    @Delete
    void delete(List<NewsItem> newsItem);
}
