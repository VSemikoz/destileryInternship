package ru.vssemikoz.newsfeed.data;

import java.util.List;

import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.tasks.GetFilteredTask;

public interface NewsStorage {

    void getFiltered(boolean favoriteNewsState, Category category, GetFilteredTask.RequestListener listener);

    void deleteAll();

    void updateItem(NewsItem item);

    void insertUnique(List<NewsItem> newsItems);

}
