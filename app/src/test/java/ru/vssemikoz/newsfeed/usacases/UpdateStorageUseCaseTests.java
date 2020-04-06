package ru.vssemikoz.newsfeed.usacases;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.usecases.UpdateNewsItemsUseCase;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UpdateStorageUseCaseTests {
    @Captor
    private ArgumentCaptor<NewsItem> itemCaptor;
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    private List<NewsItem> emptyNewsList = new ArrayList<>();
    private NewsFeedParams paramsExample;
    private Filter filterExample;
    @Mock
    NewsStorage newsStorage;
    @InjectMocks
    UpdateNewsItemsUseCase updateNewsItemsUseCase;

    @Before
    public void init(){
        initLists();
        initParams();
    }

    public void initLists(){
        for (int i = 0; i < 5; i++) {
            NewsItem item = new NewsItem();
            item.setAuthor(Integer.toString(i));
            item.setCategory(Integer.toString(i));
            item.setContent(Integer.toString(i));
            item.setDescription(Integer.toString(i));
            item.setTitle(Integer.toString(i));
            exampleNewsList.add(item);
        }
    }

    private void initParams() {
        filterExample = new Filter(Category.ALL, true);
        paramsExample = new NewsFeedParams(exampleNewsList, filterExample);
    }

    @Test
    public void verifyUpdateItemIsCalled() {
        updateNewsItemsUseCase.run(paramsExample);
        verify(newsStorage, atLeastOnce()).updateItem(any());
    }

    @Test
    public void verifyUpdateItemContainAllItemsPassedExampleList() {
        updateNewsItemsUseCase.run(paramsExample);
        verify(newsStorage, times(paramsExample.getNews().size())).updateItem(itemCaptor.capture());
        List<NewsItem> capturedArgument = itemCaptor.getAllValues();
        assertEquals(capturedArgument, exampleNewsList);
    }

    @Test
    public void verifyUpdateItemContainNoItemsPassedEmptyList() {
        NewsFeedParams paramEmptyList = new NewsFeedParams(emptyNewsList, filterExample);
        updateNewsItemsUseCase.run(paramEmptyList);
        verify(newsStorage, never()).updateItem(itemCaptor.capture());
        List<NewsItem> capturedArgument = itemCaptor.getAllValues();
        assertEquals(capturedArgument.size(), 0);
    }

}
