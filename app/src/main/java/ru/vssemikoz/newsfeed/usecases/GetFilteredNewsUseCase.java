package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class GetFilteredNewsUseCase implements BaseUseCase<Single<List<NewsItem>>, NewsFeedParams> {//inject mock
    @Inject
    NewsStorage newsStorage;

    @Inject
    public GetFilteredNewsUseCase() {
    }

    @Override
    public Single<List<NewsItem>> run(NewsFeedParams params) {
        return newsStorage.getFiltered(params.getFilter().getShowOnlyFavorite(), params.getFilter().getCategory());
    }
}
