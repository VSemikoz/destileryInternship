package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.data.NewsApiRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.mappers.NewsItemMapper;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedPresenter implements NewsFeedContract.Presenter {
    private static final String TAG = NewsFeedPresenter.class.getName();
    private final NewsFeedContract.View view;

    private boolean showOnlyFavorite = false;
    private Category category = Category.ALL;
    private List<NewsItem> news;

    private MainApplication mainApplication;
    private NewsStorage newsStorage;
    private NewsApiRepository repository;

    public NewsFeedPresenter(NewsFeedContract.View view) {
        this.view = checkNotNull(view, "tasksView cannot be null!");
        this.view.setPresenter(this);
        this.mainApplication = MainApplication.getInstance();
    }

    @Override
    public void start() {
        initStartValues();
        initApiStorage();
        initNewsStorage();
        loadNewsFromApi();
    }

    private void initApiStorage() {
        repository = new NewsApiRepository(mainApplication);
    }

    private void loadNewsFromApi() {
        view.showProgressBar();
        processNewsApiResponse();
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
        if (showOnlyFavorite && !item.isFavorite()) {
            news.remove(position);
            view.removeNewsItem(position);
            if (news.isEmpty()) {
                updateNewsListUI();
            }
        } else {
            view.updateNewsItem(position);
        }
    }

    private void updateNewsListUI(){
        if (news == null || news.isEmpty()) {
            view.setEmptyViewOnDisplay();
        } else {
            view.setRecyclerViewOnDisplay(news);
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

    private void initStartValues() {
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
    public void invertFavoriteState() {
        showOnlyFavorite = !showOnlyFavorite;
        view.setFavoriteIcon(showOnlyFavorite);
        loadNewsFromDB();
        updateNewsListUI();
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

    private void processNewsApiResponse() {
        repository.getNewsFromApi(category, new NewsApiRepository.RequestListener() {
            @Override
            public void onApiRequestSuccess(Response<NewsApiResponse> response) {
                view.hideProgressBar();
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse " + response.code());
                    return;
                }
                List<NewsItem> news = NewsItemMapper.mapResponseToNewsItems(response, category);
                newsStorage.insertUnique(news);
                loadNewsFromDB();
                updateNewsListUI();
            }

            @Override
            public void onApiRequestFailure(Throwable t) {
                view.hideProgressBar();
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        });
    }
}
