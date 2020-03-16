package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.storage.IconicStorage;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedFragment extends Fragment implements NewsFeedContract.View {
    private NewsFeedContract.Presenter mPresenter;
    private Context context;
    private NewsFeedAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ImageButton favoriteNewsButton;
    private ImageButton categoryButton;
    private ProgressBar progressBar;
    private TextView descriptionView;

    public NewsFeedFragment(){}

    public static NewsFeedFragment newInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        adapter = new NewsFeedAdapter(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(NewsFeedContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.news_feed_fragment, container, false);

        recyclerView = root.findViewById(R.id.rv_news_feed);
        emptyView = root.findViewById(R.id.tv_empty_view);
        favoriteNewsButton = root.findViewById(R.id.ib_favorite);
        progressBar = root.findViewById(R.id.progress_bar);
        descriptionView = root.findViewById(R.id.tv_description);
        categoryButton = root.findViewById(R.id.ib_category);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new NewsFeedAdapter(context);
        adapter.setOnItemClickListener(new NewsFeedAdapter.OnNewsItemClickListener() {
            @Override
            public void onChangeFavoriteStateClick(int position) {
                mPresenter.changeFavoriteState(position);
            }

            @Override
            public void OnRecyclerItemClick(int position) {
                mPresenter.openNewsDetails(position);
            }
        });

        return root;
    }

    @Override
    public void changeFavoriteIcon(ImageButton button) {
        if (mPresenter.getShowFavorite()) {
            button.setImageDrawable(IconicStorage.getYellowStarBorderless(getContext()));
        } else {
            button.setImageDrawable(IconicStorage.getWhiteStarBorderless(getContext()));
        }
    }

    @Override
    public void setEmptyViewOnDisplay() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecyclerViewOnDisplay() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void updateCategoryNameOnDescription() {
        descriptionView.setText(mPresenter.getDisplayDescriptionText());
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public NewsFeedAdapter getAdapter() {
        return adapter;
    }

}
