package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.mappers.NewsApiResponseMapper;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;

@Module
public class MapperModule {
    @Provides
    NewsMapper provideNewsMapper(NewsApiResponseMapper mapper){
        return mapper;
    }
}
