package ru.vssemikoz.newsfeed.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class LocalNewsStorage implements NewsStorage {
    private NewsItemDAO newsItemDAO;

    @Inject
    public LocalNewsStorage(NewsAppDataBase dataBase) {
        this.newsItemDAO = dataBase.newsItemDAO();
    }

    @Override
    public Single<List<NewsItem>> getFiltered(Boolean favoriteNewsState, Category category) {
        if (favoriteNewsState) {
            if (category == Category.ALL) {
                return Single.just(newsItemDAO.getFavoriteNews());
            } else {
                return Single.just(newsItemDAO.getFavoriteNewsByCategory(Category.getCategoryName(category)));
            }
        }
        if (category == Category.ALL) {
            return Single.just(newsItemDAO.getAll());
        }
        return Single.just(newsItemDAO.getNewsByCategory(Category.getCategoryName(category)));
    }

    @Override
    public void deleteAll() {
        newsItemDAO.deleteAll();
    }

    @Override
    public void updateItem(NewsItem item) {
        newsItemDAO.update(item);
    }

    @Override
    public Single<List<NewsItem>> insertUnique(List<NewsItem> newsItems) {
        newsItemDAO.insertUnique(newsItems);
        return Single.just(newsItems);
    }
}
