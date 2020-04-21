package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class UpdateNewsItemsUseCase implements BaseUseCase<Single<List<NewsItem>>, NewsFeedParams> {
    @Inject
    NewsStorage newsStorage;

    @Inject
    public UpdateNewsItemsUseCase() {
    }

    @Override
    public Single<List<NewsItem>> run(NewsFeedParams params) {
        List<NewsItem> updateList = params.getNews();
        for (NewsItem item : updateList) {
            newsStorage.updateItem(item);
        }
        return Single.just(updateList);
    }
}
