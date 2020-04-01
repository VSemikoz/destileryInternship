package ru.vssemikoz.newsfeed.data.mappers;

import java.util.List;

import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsMapper<T,P> {
    List<NewsItem> map(T mapObj, P params);
}
