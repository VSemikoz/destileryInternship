package ru.vssemikoz.newsfeed.models;

import java.util.List;

import ru.vssemikoz.newsfeed.DAO.NewsItemDAO;

public class NewsStorage {

    private NewsItemDAO newsItemDAO;

    public NewsStorage(NewsItemDAO newsItemDAO) {
        this.newsItemDAO = newsItemDAO;
    }

    public NewsItemDAO getNewsItemDAO() {
        return newsItemDAO;
    }

    public List<NewsItem> getNewsFromDB(boolean favoriteNewsState, Category category) {
        if (favoriteNewsState){
            if (category == Category.ALL){
                return newsItemDAO.getFavoriteNews();
            }else{
                return newsItemDAO.getFavoriteNewsByCategory(category.name());
            }
        }else{
            if (category == Category.ALL){
                return newsItemDAO.getAll();
            }else{
                return newsItemDAO.getNewsByCategory(category.name());
            }
        }
    }

    public void updateNews(NewsItem item){
        newsItemDAO.update(item);
    }

    public void insertUnique(List<NewsItem> newsItems){
        newsItemDAO.insertUnique(newsItems);
    }
}
