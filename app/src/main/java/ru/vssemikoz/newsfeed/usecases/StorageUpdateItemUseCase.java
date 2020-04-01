package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class StorageUpdateItemUseCase implements BaseUseCase<NewsItem, Filter> {
    private NewsStorage newsStorage;

    public StorageUpdateItemUseCase(NewsStorage newsStorage) {
        this.newsStorage = newsStorage;
    }

    @Override
    public List<NewsItem> run(List<NewsItem> itemsList, Filter params) {
        for (NewsItem item : itemsList){
            newsStorage.updateItem(item);
        }
        return null;
    }
}
