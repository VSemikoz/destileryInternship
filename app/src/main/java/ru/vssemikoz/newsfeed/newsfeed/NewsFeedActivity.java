package ru.vssemikoz.newsfeed.newsfeed;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.dialogs.PickCategoryDialog;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.utils.TypeConverters.ActivityUtils;

public class NewsFeedActivity extends AppCompatActivity implements PickCategoryDialog.OnCategorySelectedListener{
    private String TAG = NewsFeedActivity.class.getName();
    private NewsFeedPresenter newsFeedPresenter;
    private NewsFeedFragment newsFeedFragment;
    private String CURRENT_CATEGORY = "CURRENT_CATEGORY";
    private String CURRENT_SHOW_FAVORITE = "CURRENT_SHOW_FAVORITE";
    private MainApplication mainApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_act);
        mainApplication = (MainApplication) getApplicationContext();

        newsFeedFragment =
                (NewsFeedFragment) getSupportFragmentManager().findFragmentById(R.id.newsfeed_content_frame);
        if (newsFeedFragment == null) {

            newsFeedFragment = NewsFeedFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), newsFeedFragment, R.id.newsfeed_content_frame);
        }

        newsFeedPresenter = new NewsFeedPresenter(newsFeedFragment, mainApplication);

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

    @Override
    public void onCategorySelected(Category selectCategory) {
        newsFeedFragment.onCategorySelected(selectCategory);
    }
}
