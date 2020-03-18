package ru.vssemikoz.newsfeed.storage;

import java.util.List;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsStorage {

    private NewsItemDAO newsItemDAO;

    public NewsStorage(MainApplication mainApplication) {
        this.newsItemDAO = mainApplication.getNewsDataBase().newsItemDAO();
    }

    public List<NewsItem> getNewsFromDB(boolean favoriteNewsState, Category category) {
        if (favoriteNewsState) {
            if (category == Category.ALL) {
                return newsItemDAO.getFavoriteNews();
            } else {
                return newsItemDAO.getFavoriteNewsByCategory(Category.getRequestName(category));
            }
        }
        if (category == Category.ALL) {
            return newsItemDAO.getAll();
        }
        return newsItemDAO.getNewsByCategory(Category.getRequestName(category));
    }

    public void updateNews(NewsItem item) {
        newsItemDAO.update(item);
    }

    public void insertUnique(List<NewsItem> newsItems) {
        newsItemDAO.insertUnique(newsItems);
    }

    public void deleteAllNewsFromDB() {
        newsItemDAO.deleteAll();
    }
}
