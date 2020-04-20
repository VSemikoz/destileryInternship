package ru.vssemikoz.newsfeed.usecases;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class UpdateStorageUseCase implements BaseUseCase<Single<List<NewsItem>>, NewsFeedParams> {
    @Inject
    NewsStorage newsStorage;
    @Inject
    NewsRepository repository;
    @Inject
    NewsMapper mapper;

    @Inject
    public UpdateStorageUseCase() {
    }

    @Override
    public Single<List<NewsItem>> run(NewsFeedParams params) {
        return repository.getNewsFiltered(params)
                .flatMap(news -> {
                    newsStorage.insertUnique(news);
                    return Single.just(news);
                });
    }
}
