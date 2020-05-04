package ru.vssemikoz.newsfeed.usacases;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import ru.vssemikoz.newsfeed.usecases.GetFilteredNewsUseCase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetFilteredNewsUseCaseTest {
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    private List<NewsItem> emptyNewsList = new ArrayList<>();
    private NewsFeedParams paramsExample;
    @Mock
    NewsStorage newsStorage;
    @InjectMocks
    GetFilteredNewsUseCase getFilteredNewsUseCase;

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
        Filter filter = new Filter(Category.ALL, true);
        paramsExample = new NewsFeedParams(filter);
    }

    @Before
    public void init() {
        initLists();
        initParams();
    }

    @Test
    public void verifyGetFilteredIsCalled() {
        getFilteredNewsUseCase.run(paramsExample);
        verify(newsStorage).getFiltered(any(), any());
    }

    @Test
    public void verifyGetFilteredReturnNewsList() {
        when(newsStorage.getFiltered(any(), any())).thenReturn(Single.just(exampleNewsList));
        getFilteredNewsUseCase.run(paramsExample)
                .test()
                .assertValue(exampleNewsList)
                .assertNoErrors();
    }

    @Test
    public void verifyGetFilteredReturnEmptyList() {
        when(newsStorage.getFiltered(any(), any())).thenReturn(Single.just(emptyNewsList));
        getFilteredNewsUseCase.run(paramsExample)
                .test()
                .assertValue(emptyNewsList)
                .assertNoErrors();
    }

    @Test
    public void verifyGetFilteredThrowException() {
        when(newsStorage.getFiltered(any(), any())).thenReturn(Single.error(new NullPointerException()));
        getFilteredNewsUseCase.run(paramsExample)
                .test()
                .assertNoValues()
                .assertError(NullPointerException.class);
    }
}
