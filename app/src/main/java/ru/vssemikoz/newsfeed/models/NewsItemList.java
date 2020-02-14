package ru.vssemikoz.newsfeed.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItemList {
    @SerializedName("articles")
    private List<NewsItem> newsItem;

    public List<NewsItem> getNewsItem() {
        return newsItem;
    }

}
