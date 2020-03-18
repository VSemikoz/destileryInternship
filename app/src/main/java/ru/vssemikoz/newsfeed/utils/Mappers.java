package ru.vssemikoz.newsfeed.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class Mappers {
    public static List<NewsItem> mapResponseToNewsItems(Response<NewsApiResponse> response, Category category){
        List<NewsItem> news = new ArrayList<>();
        List<NewsApiResponseItem> responseItems = Objects.requireNonNull(response.body()).getNewsApiResponseItemList();
        for (NewsApiResponseItem newsApiResponseItem : responseItems) {
            news.add(new NewsItem(newsApiResponseItem, category));
        }
        return news;
    }
}
