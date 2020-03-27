package ru.vssemikoz.newsfeed.data;

import java.util.List;

import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsStorage {
    List<NewsItem> getFiltered(boolean favoriteNewsState, Category category);

    void deleteAll();

    void updateItem(NewsItem item);

    void insertUnique(List<NewsItem> newsItems);
}
