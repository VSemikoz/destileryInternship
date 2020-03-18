package ru.vssemikoz.newsfeed.storage;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public class NewsApiRepository {
    private MainApplication mainApplication;
    private Callback<NewsApiResponse> callbackNewsItemList;
    private RequestListener listener;

    public interface RequestListener{
        void onRequestSuccess(Response<NewsApiResponse> response);
        void onRequestFailure(Throwable t);
    }

    public NewsApiRepository(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public void getNewsFromApi(Category category) {
        String categoryKey = null;
        String countryKey = "ru";
        String KEY = mainApplication.getKEY();
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            categoryKey = category.name();
        }
        call = mainApplication.getNewsApi().getNews(countryKey, categoryKey, KEY);
        call.enqueue(getCallbackNewsItemList());
    }

    private Callback<NewsApiResponse> getCallbackNewsItemList() {
        if (callbackNewsItemList == null){
            initNewsItemListCallback();
        }
        return callbackNewsItemList;
    }

    private void initNewsItemListCallback() {
        callbackNewsItemList = new Callback<NewsApiResponse>() {
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
