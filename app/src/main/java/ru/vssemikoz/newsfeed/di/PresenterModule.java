package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedContract;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedPresenter;

@Module
public class PresenterModule {
    @Provides
    NewsFeedContract.Presenter provideNewsFeedPresenter(NewsFeedPresenter presenter) {
        return presenter;
    }
}
