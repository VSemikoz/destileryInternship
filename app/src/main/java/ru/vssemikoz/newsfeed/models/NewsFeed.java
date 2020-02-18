package ru.vssemikoz.newsfeed.models;

import java.util.List;

public class NewsFeed {
    private Filter filter;
    private List<NewsApiResponseItem> listOfNews;

    NewsFeed(Filter filter, List<NewsApiResponseItem> listOfNews){
        this.filter = filter;
        this.listOfNews = listOfNews;
    }

    public Filter getFilter() {
        return filter;
    }

    public List<NewsApiResponseItem> getListOfNews() {
        return listOfNews;
    }
}

