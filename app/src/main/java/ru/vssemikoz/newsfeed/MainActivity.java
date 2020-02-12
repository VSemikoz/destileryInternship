package ru.vssemikoz.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_newsfeed);

        Filter filter = new Filter("asd");
        List<NewsItem> newsItems = new ArrayList<NewsItem>();
        NewsFeedAdapter adapter = new NewsFeedAdapter(filter, newsItems);

        recyclerView.setAdapter(adapter);
    }
}
