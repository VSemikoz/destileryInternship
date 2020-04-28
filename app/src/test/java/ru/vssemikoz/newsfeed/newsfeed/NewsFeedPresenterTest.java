package ru.vssemikoz.newsfeed.newsfeed;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.vssemikoz.newsfeed.MainApplication;

import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.usecases.GetFilteredNewsUseCase;
import ru.vssemikoz.newsfeed.usecases.UpdateNewsItemsUseCase;
import ru.vssemikoz.newsfeed.usecases.UpdateStorageUseCase;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsFeedPresenterTest {
    private static final int TIME_OUT_SECONDS = 2000;
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    private NewsItem newsItemExample;
    private int indexExample = 7;

    @Mock
    List<NewsItem> news;
    @Mock
    NewsFeedContract.View view;
    @Mock
    MainApplication mainApplication;
    @Mock
    GetFilteredNewsUseCase getFilteredNewsUseCase;
    @Mock
    UpdateNewsItemsUseCase updateNewsItemsUseCase;
    @Mock
    UpdateStorageUseCase updateStorageUseCase;
    @Mock
    Navigator navigator;

    @InjectMocks
    NewsFeedPresenter presenter;

    @Before
    public void init() {
        initList();
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    private void initList() {
        int stringSize = 10;
        for (int i = 0; i < 5; i++) {
            NewsItem item = new NewsItem();
            item.setAuthor(generateRandomString(stringSize));
            item.setCategory(generateRandomString(stringSize));
            item.setContent(generateRandomString(stringSize));
            item.setDescription(generateRandomString(stringSize));
            item.setTitle(generateRandomString(stringSize));
            item.setUrl(generateRandomString(stringSize));
            item.setImageUrl(generateRandomString(stringSize));
            exampleNewsList.add(item);
        }
        newsItemExample = exampleNewsList.get(0);
    }

    private String generateRandomString(int stringSize) {
        byte[] array = new byte[stringSize];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    @Test
    public void verifySubscribe_OnNextIsCalled() {
        when(updateStorageUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.subscribe();
        verify(view).setFavoriteIcon(any());
        verify(view).setCategoryTitle(any());
        verify(view).hideProgressBar();
        verify(view).hideRefreshLayout();
    }

    @Test
    public void verifySubscribe_OnErrorIsCalled() {
        Throwable error = new RuntimeException();
        when(updateStorageUseCase.run(any())).thenReturn(Single.error(error));
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.error(error));
        presenter.subscribe();
        verify(view).setFavoriteIcon(any());
        verify(view).setCategoryTitle(any());
        verify(view).hideProgressBar();
        verify(view).hideRefreshLayout();
        verify(view).showEmptyView();
    }

    @Test
    public void verifyOpenNewsDetails_NavigatorIsCalled() {
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.openNewsDetails(eq(indexExample), mainApplication);
        verify(navigator).openWebView(any());
    }

    @Test
    public void verifyShareNewsItem_NavigatorIsCalled() {
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.shareNewsItem(anyInt());
        verify(navigator).shareFeedItem(any());
    }

    @Test
    public void verifyChangeNewsFavoriteState_ShowListIsCalled() {
        when(updateNewsItemsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.changeNewsFavoriteState(indexExample);
        verify(view).showList(exampleNewsList);
    }

    @Test
    public void verifyInvertFavoriteState_ShowListIsCalled() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.invertFavoriteState();
        verify(view).showList(exampleNewsList);
    }
}
