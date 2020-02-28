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
        Call<NewsApiResponse> call;
        String KEY = mainApplication.getKEY();

        if (category == Category.ALL) {
            call = mainApplication
                    .getNewsApi()
                    .getNews("ru", KEY);
        } else {
            call = mainApplication.getNewsApi().
                    getNewsByCategory("ru", KEY, category.name());
        }
        call.enqueue(callback);
    }

}
