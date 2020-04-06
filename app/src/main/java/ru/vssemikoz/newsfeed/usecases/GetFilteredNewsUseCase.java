package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class GetFilteredNewsUseCase implements BaseUseCase<List<NewsItem>, NewsFeedParams> {//inject mock
    @Inject
    NewsStorage newsStorage;//mock

    @Inject
    public GetFilteredNewsUseCase() {
    }

    @Override
    public List<NewsItem> run(NewsFeedParams params) {
        return newsStorage.getFiltered(params.getFilter().getShowOnlyFavorite(), params.getFilter().getCategory());
    }
}
