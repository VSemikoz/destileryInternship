package ru.vssemikoz.newsfeed.data;

import org.jetbrains.annotations.NotNull;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public class RemoteNewsRepository implements NewsRepository {
    @Inject
    NewsApi newsApi;
    @Inject
    AppConfig config;

    @Inject
    public RemoteNewsRepository() {}

    @Override
    public void getNewsFiltered(Category category, RequestListener listener) {
        String categoryKey = null;
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            categoryKey = Category.getCategoryName(category);
        }
        call = newsApi.getNews(config.getCountryKey(), categoryKey, config.getApiKey());
        call.enqueue(getNewsApiCallBack(listener));
    }

    private Callback<NewsApiResponse> getNewsApiCallBack(NewsRepository.RequestListener listener) {
        return new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsApiResponse> call, @NotNull Response<NewsApiResponse> response) {
                listener.onRequestSuccess(response);
            }

            @Override
            public void onFailure(@NotNull Call<NewsApiResponse> call, @NotNull Throwable t) {
                listener.onRequestFailure(t);
            }
        };
    }
}
