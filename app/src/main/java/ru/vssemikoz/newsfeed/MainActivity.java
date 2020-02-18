package ru.vssemikoz.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.vssemikoz.newsfeed.DAO.NewsItemDAO;
import ru.vssemikoz.newsfeed.DataBase.NewsAppDataBase;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.api.NewsApi;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class MainActivity extends AppCompatActivity {
    String KEY = "c94a57cbbb50497f94a2bb167dc91fc5";
    String MAIN_URL = "https://newsapi.org";

    NewsAppDataBase newsDataBase;
    NewsItemDAO newsItemDAO;

    Callback<NewsApiResponse> callbackNewsItemList;
    List<NewsApiResponseItem> newsApiResponseItems = new ArrayList<>();
    List<NewsItem> newsItemsFromDB = new ArrayList<>();
    NewsFeedAdapter adapter = new NewsFeedAdapter();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecView();
        initDataBase();
        initNewsItemListCallback();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<NewsApiResponse> call = newsApi.getNews("ru", KEY);
        call.enqueue(callbackNewsItemList);
    }

    void initRecView(){
        recyclerView =  findViewById(R.id.rv_news_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void initRecViewData(){
        initNewsItemsData();
        adapter.setNewsList(newsItemsFromDB);
        recyclerView.setAdapter(adapter);
    }

    void initNewsItemsData(){
        Log.d("MyLog", newsItemDAO.toString());
        newsItemsFromDB = newsItemDAO.getAll();
    }

    void initDataBase() {
        newsDataBase = Room.databaseBuilder(this,
                NewsAppDataBase.class, "news_data_base")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        newsItemDAO = newsDataBase.newsItemDAO();
    }

    void initNewsItemListCallback(){
        callbackNewsItemList = new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MyLog", "onResponse " + response.code());
                    return;
                }
                newsItemDAO.insertUnique(getNewsItemListByResponse(response));
                initRecViewData();
            }
            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                Log.d("MyLog", "onFailure " + Objects.requireNonNull(t.getMessage()));
            }
        };
    }

    List<NewsItem> getNewsItemListByResponse(Response<NewsApiResponse> response){
        ArrayList<NewsItem> newsItemList = new ArrayList<>();
        newsApiResponseItems = Objects.requireNonNull(response.body()).getNewsApiResponseItemList();
        for (NewsApiResponseItem newsApiResponseItem : newsApiResponseItems){
            newsItemList.add(new NewsItem(newsApiResponseItem));
        }
        return newsItemList;

    }
}
