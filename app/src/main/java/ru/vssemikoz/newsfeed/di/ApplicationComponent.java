package ru.vssemikoz.newsfeed.di;

import dagger.Component;
import ru.vssemikoz.newsfeed.MainApplication;

//dependencies force call fragmentComponent() in building
//@Component(dependencies = {FragmentComponent.class}, modules = {ApplicationModule.class, PresenterModule.class})
@Component(modules = {ApplicationModule.class,
                        NetworkModule.class,
                        DataBaseModule.class})
public interface ApplicationComponent {
    void inject(MainApplication mainApplication);
    FragmentComponent fragmentComponent();
}
