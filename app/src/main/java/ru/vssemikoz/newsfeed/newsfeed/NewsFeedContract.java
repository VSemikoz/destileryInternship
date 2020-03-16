package ru.vssemikoz.newsfeed.newsfeed;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter{

    }
}
