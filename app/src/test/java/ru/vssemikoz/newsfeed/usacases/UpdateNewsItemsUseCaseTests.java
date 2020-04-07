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

import retrofit2.Response;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;
import ru.vssemikoz.newsfeed.data.mappers.NewsMapper;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsFeedParams;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.models.ShowOnlyFavorite;
import ru.vssemikoz.newsfeed.usecases.UpdateStorageUseCase;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateNewsItemsUseCaseTests {
    private Boolean requestIsSuccess;
    @Captor
    private ArgumentCaptor<Category> categoryCaptor;
    @Captor
    private ArgumentCaptor<List<NewsItem>> newsItemsCaptor;
    private NewsFeedParams paramsExample;
    private List<NewsItem> exampleNewsList = new ArrayList<>();
    @Mock
    NewsStorage newsStorage;
    @Mock
    NewsRepository repository;
    @Mock
    NewsMapper mapper;
    @InjectMocks
    UpdateStorageUseCase updateStorageUseCase;


    @Before
    public void init(){
        initLists();
        initParams();
    }

    private void initParams() {
        Filter filter = new Filter(Category.ALL, ShowOnlyFavorite.SHOW);
        paramsExample = new NewsFeedParams(filter, new NewsFeedParams.RequestListener() {
            @Override
            public void onRequestSuccess(List<NewsItem> news) {
                requestIsSuccess = true;
            }

            @Override
            public void onRequestFailure() {
                requestIsSuccess = false;
            }
        });
    }

    public void initLists(){
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

    private String generateRandomString(int stringSize){
        byte[] array = new byte[stringSize];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    @Test
    public void verifyGetNewsFilteredIsCalled() {
        updateStorageUseCase.run(paramsExample);
        verify(repository).getNewsFiltered(any(), any());
    }

    @Test
    public void getNewsFilteredShouldContainCategory() {
        updateStorageUseCase.run(paramsExample);
        verify(repository).getNewsFiltered(categoryCaptor.capture(), any());
        Category capturedArgument = categoryCaptor.getValue();
        assertEquals(capturedArgument, paramsExample.getFilter().getCategory());
    }

    @Test
    public void verifyMapIsCalled() {
        Response response = mock(Response.class);
        when(response.isSuccessful()).thenReturn(true);
        RemoteNewsRepository.RequestListener listener = updateStorageUseCase.getListener(paramsExample);
        listener.onRequestSuccess(response);
        verify(mapper).map(any(), any());
    }

    @Test
    public void verifyInsertUniqueIsCalled() {
        Response response = mock(Response.class);
        when(response.isSuccessful()).thenReturn(true);
        when(mapper.map(response, paramsExample)).thenReturn(exampleNewsList);
        RemoteNewsRepository.RequestListener listener = updateStorageUseCase.getListener(paramsExample);
        listener.onRequestSuccess(response);
        verify(newsStorage).insertUnique(any());
    }

    @Test
    public void InsertUniqueShouldContainNewsList() {
        Response response = mock(Response.class);
        when(response.isSuccessful()).thenReturn(true);
        when(mapper.map(response, paramsExample)).thenReturn(exampleNewsList);
        RemoteNewsRepository.RequestListener listener = updateStorageUseCase.getListener(paramsExample);
        listener.onRequestSuccess(response);
        verify(newsStorage).insertUnique(newsItemsCaptor.capture());
        List<NewsItem> capturedArgument = newsItemsCaptor.getValue();
        assertEquals(capturedArgument, exampleNewsList);
    }

    @Test
    public void verifyRequestSuccessIsCalled() {
        Response response = mock(Response.class);
        when(response.isSuccessful()).thenReturn(true);
        RemoteNewsRepository.RequestListener listener = updateStorageUseCase.getListener(paramsExample);
        listener.onRequestSuccess(response);
        assertEquals(requestIsSuccess, true);
    }

    @Test
    public void verifyRequestFailureIsCalled() {
        Throwable throwable = mock(Throwable.class);
        RemoteNewsRepository.RequestListener listener = updateStorageUseCase.getListener(paramsExample);
        listener.onRequestFailure(throwable);
        assertEquals(requestIsSuccess, false);
    }

}
