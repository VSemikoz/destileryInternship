package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;

@Module
public class AdapterModule {

    @Provides
    NewsFeedAdapter provideAdapter(MainApplication application){
        return new NewsFeedAdapter(application.getApplicationContext());
    }
}
