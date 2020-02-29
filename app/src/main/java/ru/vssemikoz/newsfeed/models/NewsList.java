package ru.vssemikoz.newsfeed.models;

import java.util.List;

public class NewsList {
    private List<NewsItem> newsItems;

    public NewsList(List<NewsItem> newsItems) {
        this.newsItems = newsItems;
    }

    public List<NewsItem> getNewsItems() {
        return newsItems;
    }
}
