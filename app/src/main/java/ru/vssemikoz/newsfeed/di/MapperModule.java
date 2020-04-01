package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.mappers.ApiResponseMapper;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;

@Module
public class MapperModule {
    @Provides
    NewsMapper provideNewsMapper(ApiResponseMapper mapper){
        return mapper;
    }
}
