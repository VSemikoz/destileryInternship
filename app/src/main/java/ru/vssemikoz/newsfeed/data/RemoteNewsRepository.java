package ru.vssemikoz.newsfeed.data;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public class RemoteNewsRepository implements NewsRepository {
    private NewsApi newsApi;
    private String apiKey;
    private String countryKey;

    // TODO: 26.03.2020 remove keys from constructor
    public RemoteNewsRepository(NewsApi newsApi, AppConfig config) {
        this.newsApi = newsApi;
        this.apiKey = config.getApiKey();
        this.countryKey = config.getCountryKey();
    }

    @Override
    public void getNewsFiltered(Category category, RequestListener listener) {
        String categoryKey = null;
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            categoryKey = Category.getCategoryName(category);
        }
        call = newsApi.getNews(countryKey, categoryKey, apiKey);
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
