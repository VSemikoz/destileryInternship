package ru.vssemikoz.newsfeed.Tasks;

import android.os.AsyncTask;

import java.util.List;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class InsertUniqueTask extends AsyncTask<Void, Void, Void> {
    private NewsItemDAO newsItemDAO;
    private List<NewsItem> newsItems;

    public InsertUniqueTask(NewsItemDAO newsItemDAO, List<NewsItem> newsItems) {
        this.newsItemDAO = newsItemDAO;
        this.newsItems = newsItems;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        newsItemDAO.insertUnique(newsItems);
        return null;
    }
}
