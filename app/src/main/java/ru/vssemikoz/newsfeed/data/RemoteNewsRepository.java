package ru.vssemikoz.newsfeed.data;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class RemoteNewsRepository implements NewsRepository {
    @Inject
    NewsApi newsApi;
    @Inject
    AppConfig config;
    @Inject
    NewsMapper mapper;

    @Inject
    public RemoteNewsRepository() {
    }

    @Override
    public Single<List<NewsItem>> getNewsFiltered(NewsFeedParams params) {
        Category category = params.getFilter().getCategory();
        String categoryKey = null;
        if (category != Category.ALL) {
            categoryKey = Category.getCategoryName(category);
        }

        return Single.just(newsApi.getNews(config.getCountryKey(), categoryKey, config.getApiKey()))
//                .flatMap({newsApi.getNews(config.getCountryKey(), categoryKey, config.getApiKey())})
                .map(response -> mapper.map(response, params));
    }
}

//        String categoryKey = null;
//        Call<NewsApiResponse> call;
//        if (category != Category.ALL) {
//            categoryKey = Category.getCategoryName(category);
//        }
//        call = newsApi.getNews(config.getCountryKey(), categoryKey, config.getApiKey());
//        call.enqueue(getNewsApiCallBack(listener));

//    private Callback<NewsApiResponse> getNewsApiCallBack(NewsRepository.RequestListener listener) {
//        return new Callback<NewsApiResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<NewsApiResponse> call, @NotNull Response<NewsApiResponse> response) {
//                listener.onRequestSuccess(response);
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<NewsApiResponse> call, @NotNull Throwable t) {
//                listener.onRequestFailure(t);
//            }
//        };
//    }
