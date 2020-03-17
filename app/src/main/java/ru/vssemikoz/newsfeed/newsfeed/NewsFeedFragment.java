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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.dialogs.PickCategoryDialog;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.navigator.Navigator;
import ru.vssemikoz.newsfeed.storage.IconicStorage;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedFragment extends Fragment implements NewsFeedContract.View,
        PickCategoryDialog.OnCategorySelectedListener {
    private String TAG = NewsFeedFragment.class.getName();
    private String CURRENT_CATEGORY = "CURRENT_CATEGORY";
    private String CURRENT_SHOW_FAVORITE = "CURRENT_SHOW_FAVORITE";

    private NewsFeedContract.Presenter presenter;
    private Context context;
    private NewsFeedAdapter adapter;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private ImageButton favoriteNewsButton;
    private ImageButton categoryButton;
    private ProgressBar progressBar;// TODO: 16.03.2020 implement progress bar on loads
    private TextView descriptionView;

    public NewsFeedFragment(){}

    static NewsFeedFragment newInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

        context = Objects.requireNonNull(getActivity()).getApplicationContext();
        adapter = new NewsFeedAdapter(context);

//        if (savedInstanceState != null) {
//            Category category = (Category) savedInstanceState.getSerializable(CURRENT_CATEGORY);
//            Boolean showFavoriteNews = savedInstanceState.getBoolean(CURRENT_SHOW_FAVORITE);
//            presenter.setShowFavorite(showFavoriteNews);
//            presenter.setCategory(category);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCategoryNameOnDescription(Category.getDisplayName(Category.ALL));
//        setFavoriteIcon();
        presenter.start();
    }

    @Override
    public void setPresenter(NewsFeedContract.Presenter presenter) {
        Log.d(TAG, "setPresenter: ");
        this.presenter = checkNotNull(presenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.newsfeed_frag, container, false);

        recyclerView = root.findViewById(R.id.rv_news_feed);
        emptyView = root.findViewById(R.id.tv_empty_view);
        favoriteNewsButton = root.findViewById(R.id.ib_favorite);
        progressBar = root.findViewById(R.id.progress_bar);
        descriptionView = root.findViewById(R.id.tv_description);
        categoryButton = root.findViewById(R.id.ib_category);
        initRecyclerView();
        favoriteNewsButton.setOnClickListener(v -> {
            presenter.invertFavoriteState();

        });
        categoryButton.setOnClickListener(v -> {
            DialogFragment categoryDialog = new PickCategoryDialog();
            categoryDialog.onAttachFragment(this);
            categoryDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                    "categoryDialog");
        });
        return root;
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

    private void initRecyclerView(){
        adapter = new NewsFeedAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NewsFeedAdapter.OnNewsItemClickListener() {
            @Override
            public void onChangeFavoriteStateClick(int position) {
                presenter.changeNewsFavoriteState(position);
            }

            @Override
            public void OnRecyclerItemClick(int position) {
                presenter.openNewsDetails(position);
            }
        });
    }

    @Override
    public void setFavoriteIcon(Boolean showOnlyFavorite) {
        if (showOnlyFavorite) {
            favoriteNewsButton.setImageDrawable(IconicStorage.getYellowStarBorderless(context));
        } else {
            favoriteNewsButton.setImageDrawable(IconicStorage.getWhiteStarBorderless(context));
        }
    }

    @Override
    public void setCategoryTitle(Category category) {
        updateCategoryNameOnDescription(Category.getDisplayName(category));
    }

    public void setEmptyViewOnDisplay() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    public void setRecyclerViewOnDisplay() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    public void fillFragmentByView(List<NewsItem> news) {
        Log.d(TAG, "fillFragmentByView: ");
        if (news.isEmpty()) {
            setEmptyViewOnDisplay();
        } else {
            setRecyclerViewOnDisplay();
            showNews(news);
        }
    }

    private void showNews(List<NewsItem> news) {
        Log.d(TAG, "showNews: " + news);
        setRecyclerViewOnDisplay();
        adapter.setItems(news);
        adapter.notifyDataSetChanged();
    }

    public void updateCategoryNameOnDescription(String category) {
        Log.d(TAG, "updateCategoryNameOnDescription: ");
        descriptionView.setText(category);
    }

    @Override
    public void openNews(String url) {
        Navigator.openWebView(url, context);
    }

    @Override
    public void onCategorySelected(Category selectCategory) {
        presenter.setCategory(selectCategory);
        updateCategoryNameOnDescription(Category.getDisplayName(selectCategory));
        presenter.updateNews();
    }
}
