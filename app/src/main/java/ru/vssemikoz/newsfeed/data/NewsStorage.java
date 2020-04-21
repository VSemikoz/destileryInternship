package ru.vssemikoz.newsfeed.data;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsStorage {
    List<NewsItem> getFiltered(Boolean favoriteNewsState, Category category);

    void deleteAll();

    void updateItem(NewsItem item);

    Single<List<NewsItem>> insertUnique(List<NewsItem> newsItems);
}
