package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.data.NewsApiRepository;
import ru.vssemikoz.newsfeed.data.NewsStorage;
import ru.vssemikoz.newsfeed.models.Category;

@Module
public class NewsFeedPresenterModule {
    private MainApplication application;

    public MainApplication getApplication() {
        if (application == null){
            application = MainApplication.getInstance();
        }
        return application;
    }

    @Provides
    boolean provideFavoriteState(){
        return false;
    }

    @Provides
    Category provideCategory(){
        return Category.ALL;
    }

    @Provides
    MainApplication provideApplication(){
        return getApplication();
    }

    @Provides
    NewsStorage provideNewsStorage(){
        return new NewsStorage(getApplication());
    }

    @Provides
    NewsApiRepository getNewsApiRepository(){
        return new NewsApiRepository(getApplication());
    }
}
