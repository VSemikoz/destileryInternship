package ru.vssemikoz.newsfeed.storage;

import retrofit2.Call;
import retrofit2.Callback;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;


public class NewsApiRepository {
    private MainApplication mainApplication;

    public NewsApiRepository(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void getNewsFromApi(Category category, Callback<NewsApiResponse> callback) {
        String requestCategory = null;
        String country = "us";
        String KEY = mainApplication.getKEY();
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            requestCategory = category.name();
        }
        call = mainApplication.getNewsApi().getNews(country, requestCategory, KEY);
        call.enqueue(callback);
    }
}
