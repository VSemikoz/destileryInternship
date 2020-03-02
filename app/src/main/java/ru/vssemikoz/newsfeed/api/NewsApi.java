package ru.vssemikoz.newsfeed.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;

public interface NewsApi {
    @GET("/v2/top-headlines")
    Call<NewsApiResponse> getNewsByCategory(@Query("country") String country,
                                            @Query("apiKey") String key,
                                            @Query("category") String category);

    @GET("/v2/top-headlines")
    Call<NewsApiResponse> getTopNews(@Query("country") String country,
                                     @Query("apiKey") String key);

    @GET("/v2/top-headlines")
    Call<NewsApiResponse> getNewsBySource(@Query("sources") String sources,
                                          @Query("apiKey") String key);
}
