package ru.vssemikoz.newsfeed.data;

import java.util.List;

import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface DataStorage {
    List<NewsItem> getDataFromDB(boolean favoriteNewsState, Category category);

    void deleteAllDataFromDB();

    void updateData(NewsItem item);

    void insertUniqueData(List<NewsItem> newsItems);
}
