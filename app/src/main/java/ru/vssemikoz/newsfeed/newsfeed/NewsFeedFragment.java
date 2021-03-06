package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.adapters.BaseAdapter;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.data.IconicStorage;
import ru.vssemikoz.newsfeed.dialogs.PickCategoryDialog;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsFeedFragment extends Fragment implements NewsFeedContract.View,
        PickCategoryDialog.OnCategorySelectedListener {
    private static final String TAG = NewsFeedFragment.class.getName();
    private static final String CURRENT_CATEGORY = "CURRENT_CATEGORY";
    private static final String CURRENT_SHOW_FAVORITE = "CURRENT_SHOW_FAVORITE";

    @Inject
    NewsFeedContract.Presenter presenter;
    @Inject
    Context context;
    @Inject
    BaseAdapter<NewsItem> adapter;
    @Inject
    IconicStorage iconicStorage;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ImageButton favoriteNewsButton;
    private ImageButton categoryButton;
    private ProgressBar progressBar;
    private TextView descriptionView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    public NewsFeedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        MainApplication.getApplicationComponent().fragmentComponent().inject(this);
        presenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news_feed, container, false);
        initControls(root);
        return root;
    }

    private void initControls(View root) {
        initViews(root);
        favoriteNewsButton.setOnClickListener(v -> presenter.invertFavoriteState());
        categoryButton.setOnClickListener(v -> presenter.onCategoryButtonClick());
        swipeRefreshLayout.setOnRefreshListener(() ->
            presenter.updateActualNews()
        );
    }

    private void initViews(View root) {
        emptyView = root.findViewById(R.id.tv_empty_view);
        favoriteNewsButton = root.findViewById(R.id.ib_favorite);
        progressBar = root.findViewById(R.id.progress_bar);
        descriptionView = root.findViewById(R.id.tv_description);
        categoryButton = root.findViewById(R.id.ib_category);
        recyclerView = root.findViewById(R.id.rv_news_feed);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        initRecyclerView();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_CATEGORY, presenter.getCategory());
        outState.putBoolean(CURRENT_SHOW_FAVORITE, presenter.getShowFavorite());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            Category category = (Category) savedInstanceState.getSerializable(CURRENT_CATEGORY);
            Boolean showFavoriteNews = savedInstanceState.getBoolean(CURRENT_SHOW_FAVORITE);
            presenter.setShowFavorite(showFavoriteNews);
            presenter.setCategory(category);
        }

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NewsFeedAdapter.OnNewsItemClickListener() {
            @Override
            public void onChangeFavoriteStateClick(int position) {
                presenter.changeNewsFavoriteState(position);
            }

            @Override
            public void onShareButtonClick(int position) {
                presenter.shareNewsItem(position);
            }

            @Override
            public void onRecyclerItemClick(int position) {
                presenter.openNewsDetails(position, context);
            }
        });
    }

    @Override
    public void setFavoriteIcon(Boolean showOnlyFavorite) {
        if (showOnlyFavorite) {
            favoriteNewsButton.setImageDrawable(iconicStorage.getYellowStarBorderless(context));
        } else {
            favoriteNewsButton.setImageDrawable(iconicStorage.getWhiteStarBorderless(context));
        }
    }

    @Override
    public void setCategoryTitle(Category category) {
        updateCategoryNameOnDescription(Category.getDisplayName(category));
    }

    @Override
    public void updateNewsItem(int position) {
        adapter.notifyItemChanged(position);
    }

    @Override
    public void removeNewsItem(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void showCategoryDialog() {
        PickCategoryDialog categoryDialog = new PickCategoryDialog();
        categoryDialog.setListener(this);
        categoryDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                "categoryDialog");
    }

    @Override
    public void hideRefreshLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showEmptyView() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showList(List<NewsItem> news) {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        adapter.setItems(news);
        adapter.notifyDataSetChanged();
    }

    private void updateCategoryNameOnDescription(String category) {
        descriptionView.setText(category);
    }

    @Override
    public void onCategorySelected(Category selectCategory) {
        presenter.setCategory(selectCategory);
        updateCategoryNameOnDescription(Category.getDisplayName(selectCategory));
        presenter.updateActualNews();
    }
}
