package ru.vssemikoz.newsfeed.Tasks;

import android.os.AsyncTask;

import java.util.List;

import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class GetFilteredTask extends AsyncTask<Void, Void, List<NewsItem>> {
    private Boolean favoriteNewsState;
    private Category category;
    private NewsItemDAO newsItemDAO;
    private RequestListener listener;

    public interface RequestListener {
        void setNewsItems(List<NewsItem> items);
    }

    public GetFilteredTask(Boolean favoriteNewsState, Category category, NewsItemDAO newsItemDAO, RequestListener listener) {
        this.favoriteNewsState = favoriteNewsState;
        this.category = category;
        this.newsItemDAO = newsItemDAO;
        this.listener = listener;
    }

    @Override
    protected  List<NewsItem> doInBackground(Void... voids) {
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
    protected void onPostExecute(List<NewsItem> newsItems) {
        listener.setNewsItems(newsItems);
    }
}

