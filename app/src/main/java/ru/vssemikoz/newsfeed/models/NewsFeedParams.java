package ru.vssemikoz.newsfeed.models;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedParams {
    private List<NewsItem> news;
    private Filter filter;

    public NewsFeedParams(List<NewsItem> news, Filter filter) {
        this.news = news;
        this.filter = filter;
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

}
