package ru.vssemikoz.newsfeed.usacases;

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
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.usecases.UpdateStorageUseCase;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateStorageUseCaseTest {
    @Captor
    private ArgumentCaptor<NewsFeedParams> paramsCaptor;
    private NewsFeedParams paramsExample;
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    @Mock
    NewsStorage newsStorage;
    @Mock
    NewsRepository repository;
    @InjectMocks
    UpdateStorageUseCase updateStorageUseCase;


    @Before
    public void init() {
        initLists();
        initParams();
    }

    private void initParams() {
        Filter filter = new Filter(Category.ALL, true);
        paramsExample = new NewsFeedParams(exampleNewsList, filter);
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

    @Test
    public void verifyGetNewsFilteredIsCalled() {
        when(repository.getNewsFiltered(any())).thenReturn(Single.just(exampleNewsList));
        updateStorageUseCase.run(paramsExample);
        verify(repository).getNewsFiltered(any());
    }

    @Test
    public void getNewsFilteredShouldContainCategory() {
        when(repository.getNewsFiltered(any())).thenReturn(Single.just(exampleNewsList));
        updateStorageUseCase.run(paramsExample);
        verify(repository).getNewsFiltered(paramsCaptor.capture());
        NewsFeedParams capturedArgument = paramsCaptor.getValue();
        assertEquals(capturedArgument, paramsExample);
    }

    @Test
    public void insertUniqueShouldContainNewsList() {
        when(repository.getNewsFiltered(any())).thenReturn(Single.just(exampleNewsList));
        when(newsStorage.insertUnique(any())).thenReturn(Single.just(exampleNewsList));
        updateStorageUseCase.run(any())
                .test()
                .assertNoErrors()
                .assertValue(exampleNewsList);
    }

    @Test
    public void getNewsFilteredThrowException() {
        when(repository.getNewsFiltered(any())).thenReturn(Single.error(new NullPointerException()));
        updateStorageUseCase.run(any())
                .test()
                .assertNoValues()
                .assertError(NullPointerException.class);
    }
}

