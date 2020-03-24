package ru.vssemikoz.newsfeed.di;

import dagger.Component;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;

@Component(modules = {NewsFeedPresenterModule.class})
public interface NewsFeedFragmentComponent {
    //    NewsFeedPresenter getPresenter();
    void inject(NewsFeedFragment fragment);
}
