package ru.vssemikoz.newsfeed.usecases;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.Params;

public class UpdateNewsItemsUseCase implements BaseUseCase<Void, Params> {
    @Inject
    NewsStorage newsStorage;

    @Inject
    public UpdateNewsItemsUseCase() {
    }

    @Override
    public Void run(Params params) {
        for (NewsItem item : params.getNews()) {
            newsStorage.updateItem(item);
        }
        return null;
    }
}
