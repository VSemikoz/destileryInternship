package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.data.IconicStorage;
import ru.vssemikoz.newsfeed.data.LocalIconicStorage;

@Module
public class IconicStorageModule {
    @Provides
    IconicStorage provideIconicStorage(LocalIconicStorage iconicStorage){
        return iconicStorage;
    }
}
