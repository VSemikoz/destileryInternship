package ru.vssemikoz.newsfeed.data;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public class NewsApiRepository implements ApiRepository {
    private NewsApi newsApi;
    private String apiKey;
    private String countryKey;

    // TODO: 26.03.2020 remove keys from constructor
    public NewsApiRepository(NewsApi newsApi, String apiKey, String countryKey) {
        this.newsApi = newsApi;
        this.apiKey = apiKey;
        this.countryKey = countryKey;
    }

    @Override
    public void getDataFromApi(Category category, RequestListener listener) {
        String categoryKey = null;
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            categoryKey = Category.getRequestName(category);
        }
        call = newsApi.getNews(countryKey, categoryKey, apiKey);
        call.enqueue(getNewsApiCallBack(listener));
    }

    // TODO: 26.03.2020 is it need to cover override methods by method with exactly names?
    public void getNewsFromApi(Category category, RequestListener listener) {
        getDataFromApi(category, listener);
    }

    private Callback<NewsApiResponse> getNewsApiCallBack(ApiRepository.RequestListener listener) {
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
