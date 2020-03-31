package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;
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
    Navigator navigator;
    @Inject
    NewsFeedModel model;

    @Inject
    public NewsFeedPresenter() {
    }

    public void setNews(List<NewsItem> news) {
        this.news = news;
    }

    @Override
    public void start() {
        Log.d(TAG, "start: " + mainApplication);
        model.setListener(new NewsFeedModel.NewsFeedRequestListener() {
            @Override
            public void onGetRequestSuccess(List<NewsItem> news) {
                setNews(news);
                view.showList(news);
            }

            @Override
            public void onGetRequestFailure() {
                view.showEmptyView();
            }
        });
        initStartValues();
        updateNews();
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
        model.updateItem(item);
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
        model.getNews(category, showOnlyFavorite);
        updateNewsListUI();
    }

    @Override
    public void updateNewsFromApi() {
        updateNews();
    }

    private void updateNews() {
        view.showProgressBar();
        model.updateNews(category, showOnlyFavorite);
        view.hideProgressBar();
        updateNewsListUI();
    }

    private void updateNewsListUI() {
        if (news == null || news.isEmpty()) {
            view.showEmptyView();
        } else {
            view.showList(news);
        }
    }

}
