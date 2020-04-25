package ru.vssemikoz.newsfeed.usacases;

import android.util.Log;

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

import io.reactivex.rxjava3.core.Single;
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
public class UpdateNewsItemsUseCaseTest {
    @Captor
    private ArgumentCaptor<NewsItem> itemCaptor;
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    private List<NewsItem> emptyNewsList = new ArrayList<>();
    private NewsFeedParams paramsExample;
    private NewsFeedParams emptyListParamsExample;
    private Filter filterExample;
    @Mock
    NewsStorage newsStorage;
    @InjectMocks
    UpdateNewsItemsUseCase updateNewsItemsUseCase;

    @Before
    public void init() {
        initLists();
        initParams();
    }

    public void initLists() {
        int stringSize = 10;
        for (int i = 0; i < 5; i++) {
            NewsItem item = new NewsItem();
            item.setAuthor(generateRandomString(stringSize));
            item.setCategory(generateRandomString(stringSize));
            item.setContent(generateRandomString(stringSize));
            item.setDescription(generateRandomString(stringSize));
            item.setTitle(generateRandomString(stringSize));
            exampleNewsList.add(item);
        }
    }

    private String generateRandomString(int stringSize) {
        byte[] array = new byte[stringSize];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    private void initParams() {
        filterExample = new Filter(Category.ALL, true);
        paramsExample = new NewsFeedParams(exampleNewsList, filterExample);
        emptyListParamsExample = new NewsFeedParams(emptyNewsList, filterExample);
    }

    @Test
    public void updateItemShouldContainNoItems() {
        NewsFeedParams paramEmptyList = new NewsFeedParams(emptyNewsList, filterExample);
        updateNewsItemsUseCase.run(paramEmptyList);
        verify(newsStorage, never()).updateItem(itemCaptor.capture());
        List<NewsItem> capturedArgument = itemCaptor.getAllValues();
        assertEquals(capturedArgument.size(), 0);
    }

    @Test
    public void verifyRunReturnNewsList() {
        updateNewsItemsUseCase.run(paramsExample)
                .test()
                .assertValue(exampleNewsList)
                .assertNoErrors();
    }

    @Test
    public void verifyRunReturnEmptyList() {
        updateNewsItemsUseCase.run(emptyListParamsExample)
                .test()
                .assertValue(emptyNewsList)
                .assertNoErrors();
    }
}
