package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.AppConfig;
import ru.vssemikoz.newsfeed.BuildConfig;

@Module
public class AppConfigModule {
    @Provides
    AppConfig provideAppConfig() {
        return new AppConfig(BuildConfig.NEWS_API_BASE_URL, BuildConfig.NEWS_API_KEY, BuildConfig.NEWS_COUNTRY_KEY);
    }
}
