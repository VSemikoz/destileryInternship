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
import ru.vssemikoz.newsfeed.storage.IconicStorage;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedFragment extends Fragment implements NewsFeedContract.View {
    private String TAG = NewsFeedFragment.class.getName();

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
        super.onCreate(savedInstanceState);

        context = Objects.requireNonNull(getActivity()).getApplicationContext();
        adapter = new NewsFeedAdapter(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCategoryNameOnDescription();
        setFavoriteIcon();
        presenter.start();
    }

    @Override
    public void setPresenter(NewsFeedContract.Presenter presenter) {
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
            setFavoriteIcon();
            updateNews();
            fillFragmentByView();

        });
        categoryButton.setOnClickListener(v -> {
            DialogFragment categoryDialog = new PickCategoryDialog();
            categoryDialog.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(),
                    "categoryDialog");
        });
        return root;
    }

    private void initRecyclerView(){
        adapter = new NewsFeedAdapter(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NewsFeedAdapter.OnNewsItemClickListener() {
            @Override
            public void onChangeFavoriteStateClick(int position) {
                presenter.changeFavoriteState(position);
            }

            @Override
            public void OnRecyclerItemClick(int position) {
                presenter.openNewsDetails(position);
            }
        });
    }

    @Override
    public void showNews() {
        Log.d(TAG, "showNews: " + presenter.getNews());
        setRecyclerViewOnDisplay();
        List<NewsItem> newsItems = presenter.getNews();
        adapter.setItems(newsItems);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setFavoriteIcon() {
        if (presenter.getShowFavorite()) {
            favoriteNewsButton.setImageDrawable(IconicStorage.getYellowStarBorderless(context));
        } else {
            favoriteNewsButton.setImageDrawable(IconicStorage.getWhiteStarBorderless(context));
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

    private void fillFragmentByView() {
        if (presenter.getNews().isEmpty()) {
            setEmptyViewOnDisplay();
        } else {
            setRecyclerViewOnDisplay();
        }
    }

    @Override
    public void updateCategoryNameOnDescription() {
        descriptionView.setText(presenter.getDisplayDescriptionText());
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

    void onCategorySelected(Category selectCategory) {
        presenter.setCategory(selectCategory);
//        performCall();
        updateCategoryNameOnDescription();
        updateNews();
        fillFragmentByView();
    }

    private void updateNews(){
        Log.d(TAG, "updateNews: ");
        presenter.loadNews();
        adapter.setItems(presenter.getNews());
        adapter.notifyDataSetChanged();
    }
}
