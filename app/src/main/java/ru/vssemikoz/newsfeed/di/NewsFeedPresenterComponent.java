package ru.vssemikoz.newsfeed.di;

import dagger.Component;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedPresenter;

@Component(modules = {NewsFeedPresenterModule.class})
public interface NewsFeedPresenterComponent {
    void inject(NewsFeedPresenter fragment);

}
