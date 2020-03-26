package ru.vssemikoz.newsfeed.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;

@Module
public class AdapterModule {

    @Provides
    NewsFeedAdapter provideAdapter(Context context){
        return new NewsFeedAdapter(context);
    }
}
