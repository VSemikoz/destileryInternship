package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.Objects;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.storage.NewsApiRepository;
import ru.vssemikoz.newsfeed.storage.NewsStorage;
import ru.vssemikoz.newsfeed.utils.Mappers;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedPresenter implements NewsFeedContract.Presenter, NewsApiRepository.RequestListener {
    private final NewsFeedContract.View view;

    private String TAG = NewsFeedPresenter.class.getName();
    private boolean showOnlyFavorite = false;
    private Category category = Category.ALL;
    private List<NewsItem> news;

    private MainApplication mainApplication;
    private NewsStorage newsStorage;
    private NewsApiRepository repository;

    NewsFeedPresenter(NewsFeedContract.View tasksView, MainApplication mainApplication) {
        view = checkNotNull(tasksView, "tasksView cannot be null!");
        view.setPresenter(this);
        this.mainApplication = mainApplication;
    }

    @Override
    public void start() {
        initApiStorage();
        initNewsStorage();
        loadNewsFromApi();
    }

    private void initApiStorage() {
        repository = new NewsApiRepository(mainApplication);
        repository.setListener(this);
    }

    private void loadNewsFromApi() {
        view.showProgressBar();
        performCall();
    }

    private void loadNewsFromDB() {
        news = getNewsFromDB();
    }

    @Override
    public void openNewsDetails(int position, Context context) {
        NewsItem item = news.get(position);
        String url = item.getUrl();
        Navigator navigator = new Navigator();
        navigator.openWebView(url, context);
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
        newsStorage = new NewsStorage(mainApplication);
    }

    private void performCall() {
        repository.getNewsFromApi(category);
    }

    @Override
    public void onApiRequestSuccess(Response<NewsApiResponse> response) {
        view.hideProgressBar();
        if (!response.isSuccessful()) {
            Log.d(TAG, "onResponse " + response.code());
            return;
        }
        List<NewsItem> news = Mappers.mapResponseToNewsItems(response, category);
        newsStorage.insertUnique(news);
        Log.d(TAG, "onResponse: ");
        loadNewsFromDB();
        view.updateNewsListUI();
    }

    @Override
    public void onApiRequestFailure(Throwable t) {
        view.hideProgressBar();
        Log.d(TAG, "onFailure " + Objects.requireNonNull(t.getMessage()));
    }
}
