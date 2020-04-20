package ru.vssemikoz.newsfeed.api;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsApi {
    @GET("/v2/top-headlines")
    Response<NewsApiResponse> getNews(@Query("country") String country,
                                             @Query("category") String category,
                                             @Query("apiKey") String key);
}
