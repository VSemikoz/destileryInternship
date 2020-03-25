package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.data.NewsApiRepository;

@Module
public class ApiRepositoryModule {

    @Provides
    NewsApiRepository provideApiRepository(MainApplication application){
        return new NewsApiRepository(application);
    }
}
