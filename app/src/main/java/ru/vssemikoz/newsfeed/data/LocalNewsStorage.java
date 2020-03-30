package ru.vssemikoz.newsfeed.data;

import java.util.List;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.Tasks.DeleteTask;
import ru.vssemikoz.newsfeed.Tasks.GetFilteredTask;
import ru.vssemikoz.newsfeed.Tasks.InsertUniqueTask;
import ru.vssemikoz.newsfeed.Tasks.UpdateTask;
import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.database.NewsAppDataBase;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class LocalNewsStorage implements NewsStorage {
    private NewsItemDAO newsItemDAO;

    private void setResultNews(List<NewsItem> resultNews, RequestListener listener) {
        if (resultNews ==null || resultNews.isEmpty()){
            listener.onRequestFailure();
        } else {
            listener.onRequestSuccess(resultNews);
        }
    }

    @Inject
    public LocalNewsStorage(NewsAppDataBase dataBase) {
        this.newsItemDAO = dataBase.newsItemDAO();
    }

    @Override
    public void getFiltered(boolean favoriteNewsState, Category category,  RequestListener listener) {
        GetFilteredTask task = new GetFilteredTask(favoriteNewsState, category, newsItemDAO, items -> setResultNews(items, listener));
        task.execute();
    }

    @Override
    public void deleteAll() {
        DeleteTask task = new DeleteTask(newsItemDAO);
        task.execute();
    }

    @Override
    public void updateItem(NewsItem item) {
        UpdateTask task =  new UpdateTask(newsItemDAO, item);
        task.execute();
    }

    @Override
    public void insertUnique(List<NewsItem> newsItems) {
        InsertUniqueTask task = new InsertUniqueTask(newsItemDAO, newsItems);
        task.execute();
    }

}

