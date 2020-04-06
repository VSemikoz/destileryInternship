package ru.vssemikoz.newsfeed.usacases;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import ru.vssemikoz.newsfeed.usecases.GetFilteredNewsUseCase;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFilteredNewsUseCaseTests {
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    private List<NewsItem> emptyNewsList = new ArrayList<>();
    private NewsFeedParams paramsExample;
    @Mock
    NewsStorage newsStorage;
    @InjectMocks
    GetFilteredNewsUseCase getFilteredNewsUseCase;

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
        Filter filter = new Filter(Category.ALL, true);
        paramsExample = new NewsFeedParams(filter);
    }

    @Before
    public void init(){
        initLists();
        initParams();
    }

    @Test
    public void verifyGetFilteredIsCalled() {
        getFilteredNewsUseCase.run(paramsExample);
        verify(newsStorage).getFiltered(anyBoolean(), any());
    }

    @Test
    public void verifyGetFilteredReturnNewsList() {
        when(newsStorage.getFiltered(anyBoolean(), any())).thenReturn(exampleNewsList);
        assertEquals(getFilteredNewsUseCase.run(paramsExample), exampleNewsList);
    }

    @Test
    public void verifyGetFilteredReturnEmptyList() {
        when(newsStorage.getFiltered(anyBoolean(), any())).thenReturn(emptyNewsList);
        assertEquals(getFilteredNewsUseCase.run(paramsExample), emptyNewsList);
    }

    @Test
    public void verifyGetFilteredThrowException() {
        when(newsStorage.getFiltered(anyBoolean(), any())).thenThrow(new IllegalArgumentException());
        assertThrows(IllegalArgumentException.class, () -> getFilteredNewsUseCase.run(paramsExample));
    }

}
