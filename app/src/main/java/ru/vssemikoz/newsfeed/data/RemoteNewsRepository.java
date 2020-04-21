package ru.vssemikoz.newsfeed.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class RemoteNewsRepository implements NewsRepository {
    @Inject
    NewsApi newsApi;
    @Inject
    AppConfig config;
    @Inject
    NewsMapper mapper;

    @Inject
    public RemoteNewsRepository() {
    }

    @Override
    public Single<List<NewsItem>> getNewsFiltered(NewsFeedParams params) {
        Category category = params.getFilter().getCategory();
        String categoryKey = null;
        if (category != Category.ALL) {
            categoryKey = Category.getCategoryName(category);
        }

        return newsApi.getNews(config.getCountryKey(), categoryKey, config.getApiKey())
                .map(response -> mapper.map(response, params));
    }
}
