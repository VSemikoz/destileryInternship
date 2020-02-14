package ru.vssemikoz.newsfeed.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.NewsItemList;

public interface NewsApi {
    @GET("/v2/top-headlines")
    Call<NewsItemList> getNews(@Query("country") String countryName, @Query("apiKey") String key);
}
