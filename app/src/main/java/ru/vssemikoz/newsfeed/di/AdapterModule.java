package ru.vssemikoz.newsfeed.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.adapters.BaseAdapter;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.models.NewsItem;

@Module
public class AdapterModule {
    @Provides
    BaseAdapter<NewsItem> provideAdapter(NewsFeedAdapter adapter){
        return adapter;
    }
}
