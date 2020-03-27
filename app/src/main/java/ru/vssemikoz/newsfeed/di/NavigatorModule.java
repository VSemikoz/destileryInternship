package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.navigator.AppNavigator;
import ru.vssemikoz.newsfeed.navigator.Navigator;

@Module
public class NavigatorModule {
    @Provides
    Navigator provideNavigator(AppNavigator navigator){
        return navigator;
    }
}
