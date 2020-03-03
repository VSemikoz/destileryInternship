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
        String KEY = mainApplication.getKEY();
        Call<NewsApiResponse> call = null;
        if (category == Category.ALL & source == Source.ALL){
            call = mainApplication.getNewsApi().getTopNews("ru", KEY);
        }
        if (category != Category.ALL & source == Source.ALL){
            call = mainApplication.getNewsApi().getNewsByCategory("ru", KEY, category.name());
        }
        if (category == Category.ALL & source != Source.ALL){
            call = mainApplication.getNewsApi().getNewsBySource(source.toString(), KEY);
        }

        if (call != null){
            call.enqueue(callback);
        }
    }

}
