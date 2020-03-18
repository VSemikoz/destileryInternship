package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.dialogs.PickCategoryDialog;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.storage.NewsApiRepository;
import ru.vssemikoz.newsfeed.storage.NewsStorage;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedPresenter implements NewsFeedContract.Presenter {
    private final NewsFeedContract.View view;

    private String TAG = NewsFeedPresenter.class.getName();
    private boolean showOnlyFavorite = false;
    private Category category = Category.ALL;
    private List<NewsItem> news;

    private Callback<NewsApiResponse> callbackNewsItemList;
    private MainApplication mainApplication;
    private NewsStorage newsStorage;

    NewsFeedPresenter(NewsFeedContract.View tasksView, MainApplication mainApplication) {
        view = checkNotNull(tasksView, "tasksView cannot be null!");
        view.setPresenter(this);
        this.mainApplication = mainApplication;
    }

    @Override
    public void start() {
        initNewsItemListCallback();
        initNewsStorage();
        loadNewsFromApi();
    }

    private void loadNewsFromApi() {
        view.showProgressBar();
        performCall();
    }

    private void loadNewsFromDB() {
        news = getNewsFromDB();
    }

    @Override
    public void openNewsDetails(int position) {
        NewsItem item = news.get(position);
        String url = item.getUrl();
        view.showNewsDetailsUI(url);
    }

    @Override
    public void changeNewsFavoriteState(int position) {
        NewsItem item = news.get(position);
        item.invertFavoriteState();
        newsStorage.updateNews(item);
        if (showOnlyFavorite && !item.isFavorite()){
            news.remove(position);
            view.removeNewsItem(position);
            if (news.isEmpty()){
                view.updateNewsListUI();
            }
        } else {
            view.updateNewsItem(position);
        }
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public void setShowFavorite(Boolean showOnlyFavorite) {
        this.showOnlyFavorite = showOnlyFavorite;
    }

    @Override
    public Boolean getShowFavorite() {
        return this.showOnlyFavorite;
    }

    @Override
    public void initStartValues() {
        category = Category.ALL;
        showOnlyFavorite = false;
        view.setFavoriteIcon(showOnlyFavorite);
        view.setCategoryTitle(category);
    }

    @Override
    public void onCategoryButtonClick() {
        view.showCategoryDialog();
    }

    @Override
    public void onUpdateNewsList() {
        if (news == null || news.isEmpty()) {
            view.setEmptyViewOnDisplay();
        } else {
            view.setRecyclerViewOnDisplay(news);
        }
    }

    @Override
    public void openWebView(String url, Context context) {
        Navigator navigator = new Navigator();
        navigator.openWebView(url, context);
    }

    @Override
    public void invertFavoriteState() {
        showOnlyFavorite = !showOnlyFavorite;
        view.setFavoriteIcon(showOnlyFavorite);
        loadNewsFromDB();
        view.updateNewsListUI();
    }

    @Override
    public void updateNewsFromApi() {
        loadNewsFromApi();
    }

    private List<NewsItem> getNewsFromDB() {
        return newsStorage.getNewsFromDB(showOnlyFavorite, category);
    }

    private void initNewsStorage() {
        NewsItemDAO newsItemDAO = mainApplication.getNewsDataBase().newsItemDAO();
        newsStorage = new NewsStorage(newsItemDAO);
    }

    private void initNewsItemListCallback() {
        // TODO: 17.03.2020 extract in network layer
        callbackNewsItemList = new Callback<NewsApiResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsApiResponse> call, @NotNull Response<NewsApiResponse> response) {
                view.hideProgressBar();
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse " + response.code());
                    return;
                }
                newsStorage.insertUnique(getNewsItemListByResponse(response, category));// TODO: 18.03.2020 mapresponsetonewsitems
                Log.d(TAG, "onResponse: ");
                loadNewsFromDB();
                view.updateNewsListUI();
            }

            @Override
            public void onFailure(@NotNull Call<NewsApiResponse> call, @NotNull Throwable t) {
                view.hideProgressBar();
                Log.d(TAG, "onFailure " + Objects.requireNonNull(t.getMessage()));
            }
        };
    }

    private void performCall() {
        NewsApiRepository newsApiRepository = new NewsApiRepository(mainApplication);
        newsApiRepository.getNewsFromApi(category, callbackNewsItemList);
    }

    private List<NewsItem> getNewsItemListByResponse(Response<NewsApiResponse> response, Category category) {
        List<NewsItem> news = new ArrayList<>();
        List<NewsApiResponseItem> newsApiResponseItems = Objects.requireNonNull(response.body()).getNewsApiResponseItemList();
        for (NewsApiResponseItem newsApiResponseItem : newsApiResponseItems) {
            news.add(new NewsItem(newsApiResponseItem, category));
        }
        return news;
    }
}
