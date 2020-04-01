package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.usecases.StorageGetUseCase;
import ru.vssemikoz.newsfeed.usecases.StorageUpdateItemUseCase;
import ru.vssemikoz.newsfeed.usecases.StorageUpdateUseCase;

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
    NewsMapper mapper;

    @Inject
    public NewsFeedPresenter() {
    }

    @Override
    public void start() {
        Log.d(TAG, "start: " + mainApplication);
        initStartValues();
        updateActualNews();
    }

    private void updateActualNews() {
        view.showProgressBar();
        updateNewsStorage();
        getNewsFromStorage();
        updateNewsListUI();
        view.hideProgressBar();
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
        List<NewsItem> updateList = new ArrayList<>();
        updateList.add(item);
        updateItemsStorage(updateList);
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
        getNewsFromStorage();
        updateNewsListUI();
    }

    @Override
    public void updateNewsFromApi() {
        updateActualNews();
    }

    private void getNewsFromStorage() {
        StorageGetUseCase getUseCase = new StorageGetUseCase(newsStorage);
        news = getUseCase.run(null, new Filter(category, showOnlyFavorite));
    }

    private void updateNewsStorage() {
        StorageUpdateUseCase updateUseCase = new StorageUpdateUseCase(newsStorage, repository, mapper );
        updateUseCase.run(null, new Filter(category, showOnlyFavorite));
    }
    private void updateItemsStorage(List<NewsItem> updateList){
        StorageUpdateItemUseCase updateItemUseCase = new StorageUpdateItemUseCase(newsStorage);
        updateItemUseCase.run(updateList, new Filter(category, showOnlyFavorite));
    }

    private void initStartValues() {
        view.setFavoriteIcon(showOnlyFavorite);
        view.setCategoryTitle(category);
    }

    private void updateNewsListUI() {
        if (news == null || news.isEmpty()) {
            view.showEmptyView();
        } else {
            view.showList(news);
        }
    }

}
