package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;

import java.util.List;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.ShowOnlyFavorite;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

        void showEmptyView();

        void showList(List<NewsItem> news);

        void setFavoriteIcon(ShowOnlyFavorite showOnlyFavorite);

        void setCategoryTitle(Category category);

        void updateNewsItem(int position);

        void removeNewsItem(int position);

        void showProgressBar();

        void hideProgressBar();

        void showCategoryDialog();
    }

    interface Presenter extends BasePresenter {

        void updateActualNews();

        void invertFavoriteState();

        void openNewsDetails(int position, Context context);

        void changeNewsFavoriteState(int position);

        void setCategory(Category category);

        void setShowFavorite(ShowOnlyFavorite showOnlyFavorite);

        Category getCategory();

        ShowOnlyFavorite getShowFavorite();

        void onCategoryButtonClick();

        // TODO: 26.03.2020 think about better solution
        void setView(NewsFeedContract.View view);
    }
}
