package ru.vssemikoz.newsfeed.tasks;

import android.os.AsyncTask;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;

public class DeleteTask extends AsyncTask<Void, Void, Void> {
    public DeleteTask(NewsItemDAO newsItemDAO) {
        this.newsItemDAO = newsItemDAO;
    }

    private NewsItemDAO newsItemDAO;

    @Override
    protected Void doInBackground(Void... voids) {
        newsItemDAO.deleteAll();
        return null;
    }
}
