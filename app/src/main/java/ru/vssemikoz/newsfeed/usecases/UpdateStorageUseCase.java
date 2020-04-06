package ru.vssemikoz.newsfeed.usecases;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class UpdateStorageUseCase implements BaseUseCase<Void, NewsFeedParams> {
    private static final String TAG = UpdateStorageUseCase.class.getName();
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
    public Void run(NewsFeedParams params) {
        NewsFeedParams.RequestListener listener = params.getListener();
        repository.getNewsFiltered(params.getFilter().getCategory(), new RemoteNewsRepository.RequestListener() {
            @Override
            public void onRequestSuccess(Response<NewsApiResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse " + response.code());
                    return;
                }
                List<NewsItem> news = mapper.map(response, params);
                newsStorage.insertUnique(news);
                listener.onRequestSuccess(news);
            }

            @Override
            public void onRequestFailure(Throwable t) {
                listener.onRequestFailure();
            }
        });
        return null;
    }
}
