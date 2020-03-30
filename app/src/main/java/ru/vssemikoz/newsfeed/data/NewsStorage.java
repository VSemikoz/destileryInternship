package ru.vssemikoz.newsfeed.data;

import java.util.List;

import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsStorage {
    public interface RequestListener{
        void onRequestSuccess(List<NewsItem> items);
        void onRequestFailure();
    }

    void getFiltered(boolean favoriteNewsState, Category category, RequestListener listener);

    void deleteAll();

    void updateItem(NewsItem item);

    void insertUnique(List<NewsItem> newsItems);

}
