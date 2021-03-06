package ru.vssemikoz.newsfeed.data.mappers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;

public class NewsApiResponseMapper implements NewsMapper {
    @Inject
    public NewsApiResponseMapper() {
    }

    @Override
    public List<NewsItem> map(NewsApiResponse response, NewsFeedParams params) {
        List<NewsItem> news = new ArrayList<>();
        Category category = params.getFilter().getCategory();
        List<NewsApiResponseItem> responseItems = response.getNewsApiResponseItemList();
        for (NewsApiResponseItem newsApiResponseItem : responseItems) {
            news.add(new NewsItem(newsApiResponseItem, category));
        }
        return news;
    }
}
