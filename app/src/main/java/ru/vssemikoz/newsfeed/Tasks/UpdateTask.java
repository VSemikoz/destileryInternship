package ru.vssemikoz.newsfeed.Tasks;

import android.os.AsyncTask;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class UpdateTask extends AsyncTask<Void, Void, Void> {
    private NewsItemDAO newsItemDAO;
    private NewsItem item;

    public UpdateTask(NewsItemDAO newsItemDAO, NewsItem item) {
        this.newsItemDAO = newsItemDAO;
        this.item = item;
    }

    @Override
    protected Void doInBackground(Void... Void) {
        newsItemDAO.update(item);
        return null;
    }
}
