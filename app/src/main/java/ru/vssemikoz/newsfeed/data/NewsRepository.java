package ru.vssemikoz.newsfeed.data;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public interface NewsRepository {
    interface RequestListener {

        void onRequestSuccess(Response<NewsApiResponse> response);

        void onRequestFailure(Throwable t);
    }

    void getNewsFiltered(Category category, RequestListener listener);
}
