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
        String categoryKey = null;
        String countryKey = "ru";
        String KEY = mainApplication.getKEY();
        Call<NewsApiResponse> call;
        if (category != Category.ALL) {
            categoryKey = category.name();
        }
        call = mainApplication.getNewsApi().getNews(countryKey, categoryKey, KEY);
        call.enqueue(callback);
    }
}
