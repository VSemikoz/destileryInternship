package ru.vssemikoz.newsfeed.storage;

import retrofit2.Call;
import retrofit2.Callback;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.Source;


public class NewsApiRepository {
    private MainApplication mainApplication;

    public NewsApiRepository(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    public void getNewsFromApi(Category category, Callback<NewsApiResponse> callback, Source source) {
        String requestCategory = null;
        String requestSource = null;
        String country = "ru";
        String KEY = mainApplication.getKEY();
        Call<NewsApiResponse> call;

        if (category != Category.ALL) {
            requestCategory = category.name();
            requestSource = null;
        }
        if (source != Source.ALL) {
            requestSource = Source.getRequestName(source);
            country = null;
        }
        call = mainApplication.getNewsApi().getNews(country, requestCategory, requestSource, KEY);
        call.enqueue(callback);
    }
}
