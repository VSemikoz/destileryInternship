package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.data.mappers.NewsItemMapper;
import ru.vssemikoz.newsfeed.navigator.Navigator;

public class NewsFeedPresenter implements NewsFeedContract.Presenter {
    private static final String TAG = NewsFeedPresenter.class.getName();
    private NewsFeedContract.View view;

    private boolean showOnlyFavorite = false;
    private Category category = Category.ALL;
    private List<NewsItem> news;

    @Inject
    MainApplication mainApplication;
    @Inject
    NewsStorage newsStorage;
    @Inject
    NewsRepository repository;
    @Inject
    Navigator navigator;

    @Inject
    public NewsFeedPresenter() {
    }

    @Override
    public void start() {
        Log.d(TAG, "start: " + mainApplication);
        initStartValues();
        loadNewsFromApi();
    }

    private void loadNewsFromApi() {
        view.showProgressBar();
        requestNewsFromApi();
    }

    private void loadNewsFromDB() {
        getNewsFromDB();
    }

    @Override
    public void openNewsDetails(int position, Context context) {
        NewsItem item = news.get(position);
        String url = item.getUrl();
        navigator.openWebView(url);
    }

    @Override
    public void changeNewsFavoriteState(int position) {
        NewsItem item = news.get(position);
        item.invertFavoriteState();
        newsStorage.updateItem(item);
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

    private void updateNewsListUI() {
        if (news == null || news.isEmpty()) {
            view.showEmptyView();
        } else {
            view.showList(news);
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
        view.setFavoriteIcon(showOnlyFavorite);
        view.setCategoryTitle(category);
    }

    @Override
    public void onCategoryButtonClick() {
        view.showCategoryDialog();
    }

    @Override
    public void setView(NewsFeedContract.View view) {
        this.view = view;
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

    private void getNewsFromDB() {
        newsStorage.getFiltered(showOnlyFavorite, category, new NewsStorage.RequestListener() {
            @Override
            public void onRequestSuccess(List<NewsItem> items) {
                news = items;
                view.showList(news);
            }

            @Override
            public void onRequestFailure() {
                view.showEmptyView();
            }
        });
    }

    private void requestNewsFromApi() {
        repository.getNewsFiltered(category, new RemoteNewsRepository.RequestListener() {
            @Override
            public void onRequestSuccess(Response<NewsApiResponse> response) {
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
            public void onRequestFailure(Throwable t) {
                view.hideProgressBar();
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        });
    }

}
