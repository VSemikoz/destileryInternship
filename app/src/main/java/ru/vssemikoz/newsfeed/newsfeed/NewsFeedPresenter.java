package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.usecases.GetFilteredNewsUseCase;
import ru.vssemikoz.newsfeed.usecases.UpdateNewsItemsUseCase;
import ru.vssemikoz.newsfeed.usecases.UpdateStorageUseCase;

public class NewsFeedPresenter implements NewsFeedContract.Presenter {
    private static final String TAG = NewsFeedPresenter.class.getName();
    private NewsFeedContract.View view;

    private Boolean showOnlyFavorite = false;
    private Category category = Category.ALL;
    private List<NewsItem> news;

    @Inject
    CompositeDisposable disposables;
    @Inject
    MainApplication mainApplication;
    @Inject
    GetFilteredNewsUseCase getFilteredNewsUseCase;
    @Inject
    UpdateNewsItemsUseCase updateNewsItemsUseCase;
    @Inject
    UpdateStorageUseCase updateStorageUseCase;
    @Inject
    Navigator navigator;

    @Inject
    public NewsFeedPresenter() {
    }

    @Override
    public void subscribe() {
        Log.d(TAG, "start: " + mainApplication);
        Category.resolveCategory(mainApplication, Arrays.asList(Category.values()));
        initStartValues();
        updateActualNews();
    }

    @Override
    public void unsubscribe() {
        disposables.clear();
    }

    @Override
    public void updateActualNews() {
        NewsFeedParams params = new NewsFeedParams(new Filter(category, showOnlyFavorite));
        Disposable subscription =
                updateStorageUseCase.run(params)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(adda -> view.showProgressBar())
                        .subscribe(
                                taskOptional -> { /* Emit */
                                    getNewsFromStorage();
                                    updateNewsListUI();
                                    view.hideProgressBar();
                                    view.hideRefreshLayout();
                                },
                                throwable -> { /* Error */
                                    Log.e(TAG, "onRequestFailure: update request failure, shows db news" + throwable);
                                    // TODO: 20.04.2020 double code
                                    getNewsFromStorage();
                                    updateNewsListUI();
                                    view.hideProgressBar();
                                    view.hideRefreshLayout();
                                }
                        );
        disposables.add(subscription);
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
                view.showEmptyView();
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
    public void shareNewsItem(int position) {
        String messageToSend = news.get(position).getUrl();
        navigator.shareFeedItem(messageToSend);
    }

    @Override
    public void invertFavoriteState() {
        showOnlyFavorite = !showOnlyFavorite;
        view.setFavoriteIcon(showOnlyFavorite);
        getNewsFromStorage();
        updateNewsListUI();
    }

    private void getNewsFromStorage() {
        NewsFeedParams params = new NewsFeedParams(new Filter(category, showOnlyFavorite));
        news = getFilteredNewsUseCase.run(params);
    }

    private void updateItemsStorage(List<NewsItem> updateList) {
        NewsFeedParams params = new NewsFeedParams(updateList, new Filter(category, showOnlyFavorite));
        updateNewsItemsUseCase.run(params);
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
