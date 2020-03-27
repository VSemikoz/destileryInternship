package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;

@Module
public class ApiRepositoryModule {

    @Provides
    NewsRepository provideApiRepository(NewsApi newsApi, AppConfig config){
        return new RemoteNewsRepository(newsApi, config);
    }
}
