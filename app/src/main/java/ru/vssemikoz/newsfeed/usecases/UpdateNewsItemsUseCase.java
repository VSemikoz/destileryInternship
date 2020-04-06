package ru.vssemikoz.newsfeed.usecases;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class UpdateNewsItemsUseCase implements BaseUseCase<Void, NewsFeedParams> {
    @Inject
    NewsStorage newsStorage;

    @Inject
    public UpdateNewsItemsUseCase() {
    }

    @Override
    public Void run(NewsFeedParams params) {
        for (NewsItem item : params.getNews()) {
            newsStorage.updateItem(item);
        }
        return null;
    }
}
