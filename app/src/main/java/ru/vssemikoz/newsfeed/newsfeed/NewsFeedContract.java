package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;

import java.util.List;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

        void showEmptyView();

        void showList(List<NewsItem> news);

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

        void openNewsDetails(int position, Context context);

        void changeNewsFavoriteState(int position);

        void setCategory(Category category);

        void setShowFavorite(Boolean showOnlyFavorite);

        Category getCategory();

        Boolean getShowFavorite();

        void onCategoryButtonClick();

        // TODO: 26.03.2020 think about better solution
        void setView(NewsFeedContract.View view);
    }
}
