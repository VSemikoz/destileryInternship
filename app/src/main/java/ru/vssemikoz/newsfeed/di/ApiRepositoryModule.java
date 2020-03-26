package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.data.NewsApiRepository;

@Module
public class ApiRepositoryModule {

    @Provides
    NewsApiRepository provideApiRepository(NewsApi newsApi, AppConfig config){
        return new NewsApiRepository(newsApi, config.getApiKey());
    }
}
