package ru.vssemikoz.newsfeed.data;

import java.util.List;

import javax.inject.Inject;

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
    public List<NewsItem> getFiltered(boolean favoriteNewsState, Category category) {
        if (favoriteNewsState) {
            if (category == Category.ALL) {
                return newsItemDAO.getFavoriteNews();
            } else {
                return newsItemDAO.getFavoriteNewsByCategory(Category.getCategoryName(category));
            }
        }
        if (category == Category.ALL) {
            return newsItemDAO.getAll();
        }
        return newsItemDAO.getNewsByCategory(Category.getCategoryName(category));
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
    public void insertUnique(List<NewsItem> newsItems) {
        newsItemDAO.insertUnique(newsItems);
    }
}
