package ru.vssemikoz.newsfeed.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedFragment;
import ru.vssemikoz.newsfeed.newsfeed.NewsFeedPresenter;

@Module
public class NewsFeedFragmentModule {
    private NewsFeedFragment fragment;
    private Context context;

    public NewsFeedFragmentModule(NewsFeedFragment fragment) {
        this.fragment = fragment;
    }

    public Context getContext() {
        if (context == null){
            context = fragment.getActivity().getApplicationContext();
        }
        return context;
    }

    @Provides
    NewsFeedPresenter providePresenter(){
        return new NewsFeedPresenter(fragment);
    }

    @Provides
    Context provideContext(){
        return getContext();
    }

    @Provides
    NewsFeedAdapter provideRecyclerViewAdapter(){
        return new NewsFeedAdapter(getContext());
    }
}
