package ru.vssemikoz.newsfeed.data;

import java.util.List;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsStorage implements DataStorage {
    private NewsItemDAO newsItemDAO;

    public NewsStorage(NewsAppDataBase dataBase) {
        this.newsItemDAO = dataBase.newsItemDAO();
    }
    
    @Override
    public List<NewsItem> getDataFromDB(boolean favoriteNewsState, Category category) {
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

    @Override
    public void deleteAllDataFromDB() {
        newsItemDAO.deleteAll();
    }

    @Override
    public void updateData(NewsItem item) {
        newsItemDAO.update(item);
    }

    @Override
    public void insertUniqueData(List<NewsItem> newsItems) {
        newsItemDAO.insertUnique(newsItems);
    }
}
