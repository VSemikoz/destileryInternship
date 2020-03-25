package ru.vssemikoz.newsfeed.di;

import dagger.Subcomponent;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;

@Subcomponent
public interface FragmentComponent {
    void inject(NewsFeedFragment fragment);
}
