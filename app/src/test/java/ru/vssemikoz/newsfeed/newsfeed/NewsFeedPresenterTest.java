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
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NewsFeedPresenterTest {
    private static final int TIME_OUT_SECONDS = 2000;
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    private List<NewsItem> emptyList = new ArrayList<>();
    private NewsItem newsItemExample;
    private int indexExample = 7;

    @Captor
    private ArgumentCaptor<String> stringCaptor;
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
    public void verifyUpdateActualNews_UpdateStorageUseCaseIsCalled() {
        when(updateStorageUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.updateActualNews();
        verify(updateStorageUseCase).run(any());
    }

    @Test
    public void verifyUpdateActualNews_UpdateStorageUseCaseThrowError() {
        when(updateStorageUseCase.run(any())).thenReturn(Single.error(new NullPointerException()));
        updateStorageUseCase.run(any())
                .test()
                .assertNoValues()
                .assertError(NullPointerException.class);
    }

    @Test
    public void verifyInvertFavoriteState_InvertStateIsCalled() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        Boolean savedShowFavorite = presenter.getShowFavorite();
        presenter.invertFavoriteState();
        assertEquals(presenter.getShowFavorite(), !savedShowFavorite);
    }

    @Test
    public void verifyInvertFavoriteState_SetFavoriteIconIsCalled() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.invertFavoriteState();
        verify(view).setFavoriteIcon(any());
    }

    @Test
    public void verifyInvertFavoriteState_SetFavoriteIconShouldContainArguments() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        Boolean savedShowFavorite = presenter.getShowFavorite();
        presenter.invertFavoriteState();
        verify(view).setFavoriteIcon(!savedShowFavorite);
    }

    @Test
    public void verifyInvertFavoriteState_GetFilteredNewsUseCaseRunIsCalled() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.invertFavoriteState();
        verify(getFilteredNewsUseCase).run(any());
    }

    @Test
    public void verifyInvertFavoriteState_ShowListIsCalled() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.invertFavoriteState();
        verify(view).showList(exampleNewsList);
    }

    @Test
    public void verifyInvertFavoriteState_ShowEmptyViewIsCalled() {
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(emptyList));
        presenter.invertFavoriteState();
        verify(view).showEmptyView();
    }

    @Test
    public void verifyOpenNewsDetails_OpenWebViewIsCalled() {
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.openNewsDetails(anyInt(), mainApplication);
        verify(navigator).openWebView(any());
    }

    @Test
    public void verifyOpenNewsDetails_OpenWebViewShouldContainArgument() {
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.openNewsDetails(anyInt(), mainApplication);
        verify(navigator).openWebView(stringCaptor.capture());
        String capturedArgument = stringCaptor.getValue();
        assertEquals(capturedArgument, newsItemExample.getUrl());
    }

    @Test
    public void verifyChangeNewsFavoriteState_UpdateNewsItemsUseCaseRunCalled() {
        when(updateNewsItemsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        newsItemExample.setFavorite(false);
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.changeNewsFavoriteState(anyInt());
        verify(updateNewsItemsUseCase).run(any());
    }

    @Test
    public void verifyChangeNewsFavoriteState_RemoveIsCalled() {
        when(updateNewsItemsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        newsItemExample.setFavorite(true);
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.setShowFavorite(true);
        presenter.changeNewsFavoriteState(indexExample);
        verify(news, timeout(TIME_OUT_SECONDS)).remove(indexExample);
    }

    @Test
    public void verifyChangeNewsFavoriteState_ShowEmptyViewIsCalled() {
        when(updateNewsItemsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        newsItemExample.setFavorite(true);
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.setShowFavorite(true);
        when(news.isEmpty()).thenReturn(true);
        presenter.changeNewsFavoriteState(anyInt());
        verify(view, timeout(TIME_OUT_SECONDS)).showEmptyView();
    }

    @Test
    public void verifyChangeNewsFavoriteState_RemoveNewsItemIsCalled() {
        when(updateNewsItemsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        newsItemExample.setFavorite(true);
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.setShowFavorite(true);
        presenter.changeNewsFavoriteState(indexExample);
        verify(view, timeout(TIME_OUT_SECONDS)).removeNewsItem(indexExample);
    }

    @Test
    public void verifyChangeNewsFavoriteState_UpdateNewsItemIsCalled() {
        when(updateNewsItemsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        when(news.get(anyInt())).thenReturn(newsItemExample);
        presenter.setShowFavorite(false);
        presenter.changeNewsFavoriteState(indexExample);
        verify(view, timeout(TIME_OUT_SECONDS)).updateNewsItem(indexExample);
    }

    @Test
    public void verifyUpdateActualNews_ShowProgressBarIsCalled() {
        when(updateStorageUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        when(getFilteredNewsUseCase.run(any())).thenReturn(Single.just(exampleNewsList));
        presenter.updateActualNews();
        verify(view, timeout(TIME_OUT_SECONDS)).showProgressBar();
    }

    @Test
    public void verifyOnCategoryButtonClick_ShowCategoryDialogIsCalled() {
        presenter.onCategoryButtonClick();
        verify(view, timeout(TIME_OUT_SECONDS)).showCategoryDialog();
    }
}
