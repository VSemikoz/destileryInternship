package ru.vssemikoz.newsfeed.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public interface NewsApi {
    @GET("/v2/top-headlines")
    Call<NewsApiResponse> getNews(@Query("country") String country,
                                  @Query("category") String category,
                                  @Query("sources") String source,
                                  @Query("apiKey") String key);
}
