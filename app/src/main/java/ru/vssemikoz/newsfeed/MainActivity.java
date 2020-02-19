package ru.vssemikoz.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.DAO.NewsItemDAO;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.dialogs.PickCategoryDialog;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class MainActivity extends AppCompatActivity implements PickCategoryDialog.NoticeDialogListener {
    Category category = Category.all;
    String KEY;
    MainApplication mainApplication;
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
        mainApplication = (MainApplication) getApplicationContext();
        KEY = mainApplication.getKEY();

        ImageButton categoryButton = findViewById(R.id.ib_category);
        categoryButton.setOnClickListener(v -> {
            DialogFragment categoryDialog = new PickCategoryDialog();
            categoryDialog.show(getSupportFragmentManager(), "categoryDialog");
        });

        initRecView();
        initNewsItemDAO();
        initNewsItemListCallback();
        performCall();
    }

    void initRecView(){
        recyclerView =  findViewById(R.id.rv_news_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    void initRecViewData(){
        newsItemsFromDB = getNewsFromDB();
        adapter.setNewsList(newsItemsFromDB);
        recyclerView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),
                "DBSize: " + newsItemsFromDB.size(),
                Toast.LENGTH_LONG).show();
    }

    List<NewsItem> getNewsFromDB(){
        if (category == Category.all){
            return newsItemDAO.getAll();
        } else {
            return newsItemDAO.getNewsByCategory(category.name());
        }
    }

    void performCall(){
        Call<NewsApiResponse> call;
        if (category == Category.all){
            call = mainApplication.getNewsApi().getNews("ru", KEY);
        }else {
            call = mainApplication.getNewsApi().
                    getNewsByCategory("ru", KEY, category.name());
        }
        call.enqueue(callbackNewsItemList);
    }

    void initNewsItemDAO() {
        newsItemDAO = mainApplication.getNewsDataBase().newsItemDAO();
    }

    void initNewsItemListCallback(){
        callbackNewsItemList = new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                if (!response.isSuccessful()){
                    Log.d("MyLog", "onResponse " + response.code());
                    return;
                }
                newsItemDAO.insertUnique(getNewsItemListByResponse(response, category));
                initRecViewData();
            }
            @Override
            public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                Log.d("MyLog", "onFailure " + Objects.requireNonNull(t.getMessage()));
            }
        };
    }

    List<NewsItem> getNewsItemListByResponse(Response<NewsApiResponse> response, Category category){
        List<NewsItem> news = new ArrayList<>();
        newsApiResponseItems = Objects.requireNonNull(response.body()).getNewsApiResponseItemList();
        for (NewsApiResponseItem newsApiResponseItem : newsApiResponseItems){
            news.add(new NewsItem(newsApiResponseItem, category));
        }
        return news;
    }

    @Override
    public void onDialogSelectCategory(Category selectCategory) {
        performCall();
        category = selectCategory;
    }
}
