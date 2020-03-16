package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.widget.ImageButton;

import ru.vssemikoz.newsfeed.BasePresenter;
import ru.vssemikoz.newsfeed.BaseView;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.models.Category;

public interface NewsFeedContract {

    interface View extends BaseView<Presenter> {

        void changeFavoriteIcon(ImageButton button);

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

    }
}
