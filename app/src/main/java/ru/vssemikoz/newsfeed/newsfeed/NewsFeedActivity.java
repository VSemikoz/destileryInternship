package ru.vssemikoz.newsfeed.newsfeed;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.vssemikoz.newsfeed.R;

public class NewsFeedActivity extends AppCompatActivity {

    private NewsFeedPresenter newsFeedPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_fragment);

        NewsFeedFragment newsFeedFragment =
                (NewsFeedFragment) getSupportFragmentManager().findFragmentById(R.id.newsfeed_content_frame);
        if (newsFeedFragment == null){
            // TODO: 16.03.2020
            newsFeedFragment = NewsFeedFragment.newInstance();
//            ActivityUtils.addFragmentToActivity(
//                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }

        // TODO: 16.03.2020
//        newsFeedPresenter = NewsFeedPresenter(
//                Injection.provideTasksRepository(getApplicationContext()), tasksFragment);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        // TODO: 16.03.2020
    }
}
