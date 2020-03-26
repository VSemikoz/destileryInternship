package ru.vssemikoz.newsfeed.data;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public class NewsApiRepository {
    // TODO: 26.03.2020 into gradle config
    private static final String KEY = "c94a57cbbb50497f94a2bb167dc91fc5";
    private NewsApi newsApi;

    public interface RequestListener {
        void onApiRequestSuccess(Response<NewsApiResponse> response);

        void onApiRequestFailure(Throwable t);
    }

    public NewsApiRepository(NewsApi newsApi) {
        this.newsApi = newsApi;
    }

    public void getNewsFromApi(Category category, RequestListener listener) {
        String categoryKey = null;
        String countryKey = "ru";
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            categoryKey = category.name();
        }
        call = newsApi.getNews(countryKey, categoryKey, KEY);
        call.enqueue(getNewsApiCallBack(listener));
    }

    private Callback<NewsApiResponse> getNewsApiCallBack(RequestListener listener) {
        return new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsApiResponse> call, @NotNull Response<NewsApiResponse> response) {
                listener.onApiRequestSuccess(response);
            }

            @Override
            public void onFailure(@NotNull Call<NewsApiResponse> call, @NotNull Throwable t) {
                listener.onApiRequestFailure(t);
            }
        };
    }
}
