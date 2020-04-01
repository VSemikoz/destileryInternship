package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class StorageGetUseCase implements BaseUseCase<NewsItem, Filter> {
    private NewsStorage newsStorage;

    public StorageGetUseCase(NewsStorage newsStorage) {
        this.newsStorage = newsStorage;
    }

    @Override
    public List<NewsItem> run(List<NewsItem> itemsList, Filter params) {
        return newsStorage.getFiltered(params.getShowOnlyFavorite(), params.getCategory());
    }
}
