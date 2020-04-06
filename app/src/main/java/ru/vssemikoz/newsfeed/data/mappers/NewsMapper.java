package ru.vssemikoz.newsfeed.data.mappers;

import java.util.List;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;

public interface NewsMapper {
    List<NewsItem> map(Response<NewsApiResponse> response, NewsFeedParams params);
}
