package ru.vssemikoz.newsfeed.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.vssemikoz.newsfeed.models.NewsItem;

@Dao
public interface NewsItemDAO {

    @Query("SELECT * FROM NewsItem ORDER BY published_at DESC")
    List<NewsItem> getAll();

    @Query("SELECT * FROM NewsItem WHERE category == :category ORDER BY published_at DESC")
    List<NewsItem> getNewsByCategory(String category);

    @Query("SELECT * FROM NewsItem WHERE is_favorite == 1 ORDER BY published_at DESC")
    List<NewsItem> getFavoriteNews();

    @Query("SELECT * FROM NewsItem WHERE is_favorite == 1 and category == :category ORDER BY published_at DESC")
    List<NewsItem> getFavoriteNewsByCategory(String category);

    @Insert
    void insertAll(List<NewsItem> newsItems);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUnique(List<NewsItem> newsItems);

    @Update
    void update(NewsItem newsItem);

    @Query("DELETE FROM NewsItem")
    void deleteAll();
}
