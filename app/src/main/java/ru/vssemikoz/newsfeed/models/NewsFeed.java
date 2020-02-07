package ru.vssemikoz.newsfeed.models;

import java.util.List;

public class NewsFeed {
    private Filter filter;
    private List<News> listOfNews;

    NewsFeed(Filter filter, List<News> listOfNews){
        this.filter = filter;
        this.listOfNews = listOfNews;
    }

    public Filter getFilter() {
        return filter;
    }

    public List<News> getListOfNews() {
        return listOfNews;
    }
}

