package ru.vssemikoz.newsfeed.models;

import java.util.ArrayList;
import java.util.List;

public class Params {
    private List<NewsItem> news;
    private Filter filter;
    private RequestListener listener;

    public interface RequestListener {
        void onRequestSuccess(List<NewsItem> news);

        void onRequestFailure();
    }

    public Params(List<NewsItem> news, Filter filter, RequestListener listener) {
        this.news = news;
        this.filter = filter;
        this.listener = listener;
    }

    public Params(Filter filter, RequestListener listener) {
        this(new ArrayList<>(), filter, listener);
    }

    public Params(List<NewsItem> news, Filter filter) {
        this(news, filter, null);
    }

    public Params(Filter filter) {
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
            throw new NullPointerException("Params.RequestListener must be implement");
        }
        return listener;
    }
}
