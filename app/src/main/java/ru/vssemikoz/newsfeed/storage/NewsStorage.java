package ru.vssemikoz.newsfeed.storage;

import java.util.List;

import ru.vssemikoz.newsfeed.DAO.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsStorage {

    private NewsItemDAO newsItemDAO;

    public NewsStorage(NewsItemDAO newsItemDAO) {
        this.newsItemDAO = newsItemDAO;
    }

    public List<NewsItem> getNewsFromDB(boolean favoriteNewsState, Category category) {
        if (favoriteNewsState) {
            if (category == Category.ALL) {
                return newsItemDAO.getFavoriteNews();
            } else {
                return newsItemDAO.getFavoriteNewsByCategory(category.name());
            }
        } else {
            if (category == Category.ALL) {
                return newsItemDAO.getAll();
            } else {
                return newsItemDAO.getNewsByCategory(category.name());
            }
        }
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
