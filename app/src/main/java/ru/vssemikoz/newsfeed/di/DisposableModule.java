package ru.vssemikoz.newsfeed.di;

import dagger.Module;
import dagger.Provides;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@Module
public class DisposableModule {
    @Provides
    CompositeDisposable provideDisposable(){
        return new CompositeDisposable();
    }
}
