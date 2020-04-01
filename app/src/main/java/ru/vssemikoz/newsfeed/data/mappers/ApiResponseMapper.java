package ru.vssemikoz.newsfeed.data.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class ApiResponseMapper implements NewsMapper<Response<NewsApiResponse>, Category> {
    @Inject
    public ApiResponseMapper() {
    }

    @Override
    public List<NewsItem> map(Response<NewsApiResponse> mapObj, Category params) {
        List<NewsItem> news = new ArrayList<>();
        List<NewsApiResponseItem> responseItems = Objects.requireNonNull(mapObj.body()).getNewsApiResponseItemList();
        for (NewsApiResponseItem newsApiResponseItem : responseItems) {
            news.add(new NewsItem(newsApiResponseItem, params));
        }
        return news;
    }
}
