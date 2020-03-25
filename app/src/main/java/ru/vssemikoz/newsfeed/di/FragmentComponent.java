package ru.vssemikoz.newsfeed.di;

import dagger.Subcomponent;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;

@Subcomponent(modules = {AdapterModule.class, PresenterModule.class, ApiRepositoryModule.class, StorageModule.class,})
public interface FragmentComponent {
    void inject(NewsFeedFragment fragment);
}
