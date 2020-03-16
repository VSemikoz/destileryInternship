package ru.vssemikoz.newsfeed.newsfeed;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.utils.TypeConverters.ActivityUtils;

public class NewsFeedActivity extends AppCompatActivity {

    private NewsFeedPresenter newsFeedPresenter;
    private String CURRENT_CATEGORY = "CURRENT_CATEGORY";
    private String CURRENT_SHOW_FAVORITE = "CURRENT_SHOW_FAVORITE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_fragment);

        NewsFeedFragment newsFeedFragment =
                (NewsFeedFragment) getSupportFragmentManager().findFragmentById(R.id.newsfeed_content_frame);
        if (newsFeedFragment == null) {
            newsFeedFragment = NewsFeedFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), newsFeedFragment, R.id.newsfeed_content_frame);
        }

        newsFeedPresenter = new NewsFeedPresenter(newsFeedFragment);

        if (savedInstanceState != null) {
            Category category =
                    (Category) savedInstanceState.getSerializable(CURRENT_CATEGORY);
            Boolean showFavoriteNews = (Boolean) savedInstanceState.getBoolean(CURRENT_SHOW_FAVORITE);
            newsFeedPresenter.setShowFavorite(showFavoriteNews);
            newsFeedPresenter.setCategory(category);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putSerializable(CURRENT_CATEGORY, newsFeedPresenter.getCategory());
        outState.getBoolean(CURRENT_SHOW_FAVORITE, newsFeedPresenter.getShowFavorite());
    }
}
