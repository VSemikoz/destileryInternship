package ru.vssemikoz.newsfeed.newsfeed;

import java.util.List;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

        void showNewsDetailsUI(String url);

        void updateNewsListUI(List<NewsItem> news);

        void setFavoriteIcon(Boolean showOnlyFavorite);

        void setCategoryTitle(Category category);

        void updateNewsItem(int position);

        void removeNewsItem(int position);

        void showProgressBar();

        void hideProgressBar();
    }

    interface Presenter extends BasePresenter {

        void updateNewsFromApi();

        void invertFavoriteState();

        void openNewsDetails(int position);

        void changeNewsFavoriteState(int position);

        void setCategory(Category category);

        void setShowFavorite(Boolean showOnlyFavorite);

        Category getCategory();

        Boolean getShowFavorite();

        void initStartValues();
    }
}
