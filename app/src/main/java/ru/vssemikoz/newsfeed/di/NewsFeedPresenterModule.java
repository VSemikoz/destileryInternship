package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedPresenter;

@Module
public class NewsFeedPresenterModule {
    private NewsFeedFragment fragment;

    public NewsFeedPresenterModule(NewsFeedFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    NewsFeedPresenter providePresenter(){
        return new NewsFeedPresenter(fragment);
    }
}
