package ru.vssemikoz.newsfeed.di;

import dagger.Component;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;

@Component(modules = {NewsFeedFragmentModule.class})
public interface NewsFeedFragmentComponent {
    void inject(NewsFeedFragment fragment);
}
