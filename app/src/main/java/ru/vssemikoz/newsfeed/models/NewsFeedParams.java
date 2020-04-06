package ru.vssemikoz.newsfeed.models;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedParams {
    private List<NewsItem> news;
    private Filter filter;
    private RequestListener listener;

    public interface RequestListener {
        void onRequestSuccess(List<NewsItem> news);

        void onRequestFailure();
    }

    public NewsFeedParams(List<NewsItem> news, Filter filter, RequestListener listener) {
        this.news = news;
        this.filter = filter;
        this.listener = listener;
    }

    public NewsFeedParams(Filter filter, RequestListener listener) {
        this(new ArrayList<>(), filter, listener);
    }

    public NewsFeedParams(List<NewsItem> news, Filter filter) {
        this(news, filter, null);
    }

    public NewsFeedParams(Filter filter) {
        this(new ArrayList<>(), filter);
    }

    public List<NewsItem> getNews() {
        return news;
    }

    public Filter getFilter() {
        return filter;
    }

    public RequestListener getListener() {
        if (listener == null){
            throw new NullPointerException("NewsFeedParams.RequestListener must be implement");
        }
        return listener;
    }
}
