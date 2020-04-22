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
    private List<NewsItem> cashedNews;
    private CompositeDisposable disposables;

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
        disposables = new CompositeDisposable();
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
        Disposable subscription = updateStorageUseCase.run(params)
                .flatMap(updatedNews -> getFilteredNewsUseCase.run(params))
                .doOnSubscribe(disposable -> view.showProgressBar())
                .onErrorResumeNext(throwable -> getFilteredNewsUseCase.run(params))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        resultNews -> { /* Emit */
                            setCashedNews(resultNews);
                            updateNewsListUI(resultNews);
                            view.hideProgressBar();
                            view.hideRefreshLayout();
                        },
                        throwable -> { /* Error */
                            Log.e(TAG, "updateActualNews failure: " + throwable);
                            view.hideProgressBar();
                            view.hideRefreshLayout();
                            view.showEmptyView();
                        }
                );
        disposables.add(subscription);
    }

    @Override
    public void openNewsDetails(int position, Context context) {
        NewsItem item = cashedNews.get(position);
        String url = item.getUrl();
        navigator.openWebView(url);
    }

    @Override
    public void changeNewsFavoriteState(int position) {
        NewsItem item = cashedNews.get(position);
        item.invertFavoriteState();
        List<NewsItem> updateList = new ArrayList<>();
        updateList.add(item);
        NewsFeedParams params = new NewsFeedParams(updateList, new Filter(category, showOnlyFavorite));
        Disposable subscription = updateNewsItemsUseCase.run(params)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(updatedNews -> {
                            if (showOnlyFavorite && !item.isFavorite()) {
                                cashedNews.remove(position);
                                view.removeNewsItem(position);
                                if (cashedNews.isEmpty()) {
                                    view.showEmptyView();
                                }
                            } else {
                                view.updateNewsItem(position);
                            }
                        },
                        throwable -> Log.e(TAG, "changeNewsFavoriteState: " + throwable));
        disposables.add(subscription);
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
        String messageToSend = cashedNews.get(position).getUrl();
        navigator.shareFeedItem(messageToSend);
    }

    @Override
    public void invertFavoriteState() {
        showOnlyFavorite = !showOnlyFavorite;
        view.setFavoriteIcon(showOnlyFavorite);
        NewsFeedParams params = new NewsFeedParams(new Filter(category, showOnlyFavorite));
        Disposable subscription = getFilteredNewsUseCase.run(params).
                subscribe(
                        result -> {//onEmit
                            updateNewsListUI(result);
                            setCashedNews(result);
                        },
                        throwable -> Log.e(TAG, "invertFavoriteState: " + throwable)//onError
                );
        disposables.add(subscription);
    }

    private void initStartValues() {
        view.setFavoriteIcon(showOnlyFavorite);
        view.setCategoryTitle(category);
    }

    private void updateNewsListUI(List<NewsItem> newsList) {
        if (newsList == null || newsList.isEmpty()) {
            view.showEmptyView();
        } else {
            view.showList(newsList);
        }
    }

    public void setCashedNews(List<NewsItem> cashedNews) {
        this.cashedNews = cashedNews;
    }
}
