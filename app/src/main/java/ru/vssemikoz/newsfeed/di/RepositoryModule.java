package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;

@Module
public class RepositoryModule {
    @Provides
    NewsRepository provideRepository(RemoteNewsRepository repository) {
        return repository;
    }
}
