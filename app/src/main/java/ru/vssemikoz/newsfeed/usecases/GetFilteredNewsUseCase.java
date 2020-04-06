package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.Params;

public class GetFilteredNewsUseCase implements BaseUseCase<List<NewsItem>, Params> {//inject mock
    @Inject
    NewsStorage newsStorage;//mock

    @Inject
    public GetFilteredNewsUseCase() {
    }

    @Override
    public List<NewsItem> run(Params params) {
        return newsStorage.getFiltered(params.getFilter().getShowOnlyFavorite(), params.getFilter().getCategory());
    }
}
