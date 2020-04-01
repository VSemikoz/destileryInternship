package ru.vssemikoz.newsfeed.usecases;

import android.util.Log;

import java.util.List;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class StorageUpdateUseCase implements BaseUseCase<NewsItem, Filter>{
    private static final String TAG = StorageUpdateUseCase.class.getName();

    private NewsStorage newsStorage;
    private NewsRepository repository;
    private NewsMapper mapper;

    public StorageUpdateUseCase(NewsStorage newsStorage, NewsRepository repository, NewsMapper mapper) {
        this.newsStorage = newsStorage;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<NewsItem> run(List<NewsItem> itemsList, Filter params) {
        repository.getNewsFiltered(params.getCategory(), new RemoteNewsRepository.RequestListener() {
            @Override
            public void onRequestSuccess(Response<NewsApiResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse " + response.code());
                    return;
                }
                List<NewsItem> news = mapper.map(response, params);
                newsStorage.insertUnique(news);

            }

            @Override
            public void onRequestFailure(Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        });
        return null;
    }
}
