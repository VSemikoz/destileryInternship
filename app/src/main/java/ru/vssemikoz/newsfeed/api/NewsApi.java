package ru.vssemikoz.newsfeed.api;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public interface NewsApi {
    @GET("/v2/top-headlines")
    Single<NewsApiResponse> getNews(@Query("country") String country,
                                    @Query("category") String category,
                                    @Query("apiKey") String key);
}
