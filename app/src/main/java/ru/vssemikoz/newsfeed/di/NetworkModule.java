package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.api.NewsApi;

@Module
public class NetworkModule {
    // TODO: 26.03.2020 into gradle config
    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    NewsApi provideNewsApi(Retrofit retrofit){
        return retrofit.create(NewsApi.class);
    }
}
