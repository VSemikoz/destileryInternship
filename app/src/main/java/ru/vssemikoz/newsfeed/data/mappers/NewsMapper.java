package ru.vssemikoz.newsfeed.data.mappers;

import java.util.List;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.Params;

public interface NewsMapper {
    List<NewsItem> map(Response<NewsApiResponse> response, Params params);
}
