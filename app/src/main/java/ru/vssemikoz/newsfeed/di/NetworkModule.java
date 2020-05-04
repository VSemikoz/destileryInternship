package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;

@Module
public class NetworkModule {
    @Provides
    Retrofit provideRetrofit(AppConfig config) {
        return new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    @Provides
    NewsApi provideNewsApi(Retrofit retrofit) {
        return retrofit.create(NewsApi.class);
    }
}
