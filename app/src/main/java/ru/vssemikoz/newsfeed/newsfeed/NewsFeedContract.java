package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;

import java.util.List;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

        void showNewsDetailsUI(String url);

        void setEmptyViewOnDisplay();

        void setRecyclerViewOnDisplay(List<NewsItem> news);

        void updateNewsListUI();

        void setFavoriteIcon(Boolean showOnlyFavorite);

        void setCategoryTitle(Category category);

        void updateNewsItem(int position);

        void removeNewsItem(int position);

        void showProgressBar();

        void hideProgressBar();

        void showCategoryDialog();
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

        void onCategoryButtonClick();

        void onUpdateNewsList();

        void openWebView(String url, Context context);
    }
}
