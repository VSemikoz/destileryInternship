package ru.vssemikoz.newsfeed.di;

import dagger.Component;
import ru.vssemikoz.newsfeed.MainApplication;

@Component(modules = {ApplicationModule.class,
        NetworkModule.class,
        DataBaseModule.class,
        AppConfigModule.class})
public interface ApplicationComponent {
    void inject(MainApplication mainApplication);

    FragmentComponent fragmentComponent();
}
