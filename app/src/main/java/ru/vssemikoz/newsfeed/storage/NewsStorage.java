package ru.vssemikoz.newsfeed.storage;

import java.util.ArrayList;
import java.util.List;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.Source;

public class NewsStorage {

    private NewsItemDAO newsItemDAO;

    public NewsStorage(NewsItemDAO newsItemDAO) {
        this.newsItemDAO = newsItemDAO;
    }

    public List<NewsItem> getNewsFromDB(boolean favoriteNewsState, Category category, Source source) {
        if (favoriteNewsState & category == Category.ALL & source == Source.ALL) {
            return newsItemDAO.getFavoriteNews();
        }
        if (favoriteNewsState & category != Category.ALL & source == Source.ALL) {
            return newsItemDAO.getFavoriteNewsByCategory(Category.getRequestName(category));
        }
        if (favoriteNewsState & category == Category.ALL & source != Source.ALL) {
            return newsItemDAO.getFavoriteNewsBySource(Source.getRequestName(source));
        }
        if (!favoriteNewsState & category == Category.ALL & source == Source.ALL) {
            return newsItemDAO.getAll();
        }
        if (!favoriteNewsState & category != Category.ALL & source == Source.ALL) {
            return newsItemDAO.getNewsByCategory(Category.getRequestName(category));
        }
        if (!favoriteNewsState & category == Category.ALL & source != Source.ALL) {
            return newsItemDAO.getNewsBySource(Source.getRequestName(source));
        }
        return new ArrayList<>();
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
