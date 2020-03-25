package ru.vssemikoz.newsfeed.di;

import dagger.Component;

@Component(dependencies = {FragmentComponent.class}, modules = {ApplicationModule.class, PresenterModule.class})
public interface ApplicationComponent {
    FragmentComponent fragmentComponent();
}
