package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;

import java.util.List;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

        void showNews();

        void setFavoriteIcon();

        void setEmptyViewOnDisplay();

        void setRecyclerViewOnDisplay();

        void updateCategoryNameOnDescription();

        Context getContext();

        NewsFeedAdapter getAdapter();
    }

    interface Presenter extends BasePresenter{

        void loadNews();

        String getDisplayDescriptionText();

        void openNewsDetails(int position);

        void changeFavoriteState(int position);

        void setCategory(Category category);

        Category getCategory();

        void setShowFavorite(Boolean showOnlyFavorite);

        Boolean getShowFavorite();

        List<NewsItem> getNews();

        void invertFavoriteState();

    }
}
