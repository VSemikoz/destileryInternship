package ru.vssemikoz.newsfeed.di;

import dagger.Subcomponent;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;

@Subcomponent(modules = {AdapterModule.class,
        PresenterModule.class,
        RepositoryModule.class,
        StorageModule.class,
        IconicStorageModule.class})
public interface FragmentComponent {
    void inject(NewsFeedFragment fragment);
}
