package ru.vssemikoz.newsfeed.data.mappers;

import java.util.List;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsMapper<P> {
    List<NewsItem> map(Response<NewsApiResponse> response, P params);
}
