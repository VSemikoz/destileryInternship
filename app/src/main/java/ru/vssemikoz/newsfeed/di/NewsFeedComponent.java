package ru.vssemikoz.newsfeed.di;

import dagger.Component;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedContract;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedPresenter;


@Component(modules = {NewsFeedModule.class})
//@Component
public interface NewsFeedComponent {
    NewsFeedPresenter getPresenter();
//    void inject(NewsFeedFragment fragment);
//    NewsFeedContract.View getFragment();
}
