package ru.vssemikoz.newsfeed.newsfeed;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import ru.vssemikoz.newsfeed.data.NewsRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.data.RemoteNewsRepository;
import ru.vssemikoz.newsfeed.data.mappers.NewsItemMapper;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsApiResponse;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsFeedModel {
    private static final String TAG = NewsFeedModel.class.getName();
    private NewsFeedRequestListener listener;

    @Inject
    NewsStorage newsStorage;

    @Inject
    NewsRepository repository;

    public interface NewsFeedRequestListener {
        void onGetRequestSuccess(List<NewsItem> news);

        void onGetRequestFailure();

    }

    public void setListener(NewsFeedRequestListener listener) {
        this.listener = listener;
    }

    @Inject
    public NewsFeedModel() {
    }

    public void getNews(Category category, boolean showOnlyFavorite) {
        checkListener();
        List<NewsItem> news = newsStorage.getFiltered(showOnlyFavorite, category);
        listener.onGetRequestSuccess(news);
    }

    public void updateNews(Category category, boolean showOnlyFavorite) {
        checkListener();
        repository.getNewsFiltered(category, new RemoteNewsRepository.RequestListener() {
            @Override
            public void onRequestSuccess(Response<NewsApiResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse " + response.code());
                    return;
                }
                List<NewsItem> mappedNews = NewsItemMapper.mapResponseToNewsItems(response, category);
                newsStorage.insertUnique(mappedNews);
                getNews(category, showOnlyFavorite);
            }

            @Override
            public void onRequestFailure(Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
                listener.onGetRequestFailure();
            }
        });
    }

    public void updateItem(NewsItem item) {
        newsStorage.updateItem(item);
    }

    private void checkListener(){
        if (listener == null){
            throw new NullPointerException("NewsFeedRequestListener must be implemented");
        }
    }
}
