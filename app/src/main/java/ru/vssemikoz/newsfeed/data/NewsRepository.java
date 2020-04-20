package ru.vssemikoz.newsfeed.data;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsRepository {
    interface RequestListener {

        void onRequestSuccess(Response<NewsApiResponse> response);

        void onRequestFailure(Throwable t);
    }

    Single<List<NewsItem>> getNewsFiltered(NewsFeedParams params);
}
