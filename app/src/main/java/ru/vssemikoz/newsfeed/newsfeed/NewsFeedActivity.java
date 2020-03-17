package ru.vssemikoz.newsfeed.newsfeed;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.utils.TypeConverters.ActivityUtils;

public class NewsFeedActivity extends AppCompatActivity{
    private String TAG = NewsFeedActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsfeed_act);
        MainApplication mainApplication = (MainApplication) getApplicationContext();

        NewsFeedFragment newsFeedFragment = (NewsFeedFragment) getSupportFragmentManager().findFragmentById(R.id.newsfeed_content_frame);
        if (newsFeedFragment == null) {

            newsFeedFragment = NewsFeedFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), newsFeedFragment, R.id.newsfeed_content_frame);
        }

        new NewsFeedPresenter(newsFeedFragment, mainApplication);
    }
}
