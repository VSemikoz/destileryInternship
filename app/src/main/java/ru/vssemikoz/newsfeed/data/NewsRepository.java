package ru.vssemikoz.newsfeed.data;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsRepository {
    Single<List<NewsItem>> getNewsFiltered(NewsFeedParams params);
}
