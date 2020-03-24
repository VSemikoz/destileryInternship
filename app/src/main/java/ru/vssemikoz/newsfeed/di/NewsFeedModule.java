package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedPresenter;

@Module
public class NewsFeedModule {
    private NewsFeedFragment fragment;

    public NewsFeedModule(NewsFeedFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    NewsFeedPresenter providePresenter(){
        return new NewsFeedPresenter(fragment);
    }
}
