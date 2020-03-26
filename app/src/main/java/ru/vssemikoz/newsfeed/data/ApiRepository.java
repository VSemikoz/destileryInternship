package ru.vssemikoz.newsfeed.data;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public interface ApiRepository {
    interface RequestListener{

        void onRequestSuccess(Response<NewsApiResponse> response);

        void onRequestFailure(Throwable t);
    }

    void getDataFromApi(Category category, RequestListener listener);
}
