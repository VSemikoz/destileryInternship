package ru.vssemikoz.newsfeed.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsApiResponse {
    @SerializedName("articles")
    private List<NewsApiResponseItem> newsApiResponseItemList;

    public List<NewsApiResponseItem> getNewsApiResponseItemList() {
        return newsApiResponseItemList;
    }
}
