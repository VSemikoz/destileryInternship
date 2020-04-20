package ru.vssemikoz.newsfeed.data.mappers;

import java.util.List;

import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;

public interface NewsMapper {
    List<NewsItem> map(NewsApiResponse response, NewsFeedParams params);
}
