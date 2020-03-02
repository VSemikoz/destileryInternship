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
        String KEY = mainApplication.getKEY();
        Call<NewsApiResponse> call;

        if (category == Category.ALL) {
            call = mainApplication
                    .getNewsApi()
                    .getTopNews("ru", KEY);
            call.enqueue(callback);

            call = mainApplication
                    .getNewsApi()
                    .getNewsBySource(mainApplication.getNewsSource(), KEY);
            call.enqueue(callback);


        } else {
            call = mainApplication.getNewsApi().
                    getNewsByCategory("ru", KEY, category.name());
            call.enqueue(callback);

        }
    }

}
