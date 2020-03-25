package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.api.NewsApi;

@Module
public class NetworkModule {
    private String mainUrl;

    public NetworkModule(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(mainUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    NewsApi provideNewsApi(Retrofit retrofit){
        return retrofit.create(NewsApi.class);
    }
}
