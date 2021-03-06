package ru.vssemikoz.newsfeed.newsfeed;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.utils.ActivityUtils;

public class NewsFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        NewsFeedFragment newsFeedFragment = (NewsFeedFragment) getSupportFragmentManager().findFragmentById(R.id.newsfeed_content_frame);
        if (newsFeedFragment == null) {
            newsFeedFragment = new NewsFeedFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), newsFeedFragment, R.id.newsfeed_content_frame);
        }
    }
}
