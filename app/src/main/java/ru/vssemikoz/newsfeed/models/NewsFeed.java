package ru.vssemikoz.newsfeed.models;

import java.util.List;

public class NewsFeed {
    private Filter filter;
    private List<NewsItem> listOfNews;

    NewsFeed(Filter filter, List<NewsItem> listOfNews){
        this.filter = filter;
        this.listOfNews = listOfNews;
    }

    public Filter getFilter() {
        return filter;
    }

    public List<NewsItem> getListOfNews() {
        return listOfNews;
    }
}

