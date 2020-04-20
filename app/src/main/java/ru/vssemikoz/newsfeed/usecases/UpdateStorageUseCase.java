package ru.vssemikoz.newsfeed.usecases;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class UpdateStorageUseCase implements BaseUseCase<Single<List<NewsItem>>, NewsFeedParams> {
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
    public Single<List<NewsItem>> run(NewsFeedParams params) {
//        repository.getNewsFiltered(params.getFilter().getCategory(), getListener(params));
//        return null;

        return repository.getNewsFiltered(params)
                .doOnSubscribe(news -> newsStorage.insertUnique(news))
//                .subscribe(news -> newsStorage.insertUnique(news))
    }

//    public RemoteNewsRepository.RequestListener getListener(NewsFeedParams params) {
//        NewsFeedParams.RequestListener listener = params.getListener();
//        return new NewsRepository.RequestListener() {
//            @Override
//            public void onRequestSuccess(Response<NewsApiResponse> response) {
//                if (!response.isSuccessful()) {
//                    Log.d(TAG, "onResponse " + response.code());
//                    return;
//                }
//                List<NewsItem> news = mapper.map(response, params);
//                newsStorage.insertUnique(news);
//                listener.onRequestSuccess(news);
//            }
//
//            @Override
//            public void onRequestFailure(Throwable t) {
//                listener.onRequestFailure();
//            }
//        };
//    }
}
