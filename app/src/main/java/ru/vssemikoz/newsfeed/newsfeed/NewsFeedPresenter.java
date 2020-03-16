package ru.vssemikoz.newsfeed.newsfeed;

import android.content.Intent;
import android.net.Uri;

import java.util.List;

import ru.vssemikoz.newsfeed.MainApplication;
import ru.vssemikoz.newsfeed.adapters.NewsFeedAdapter;
import ru.vssemikoz.newsfeed.dao.NewsItemDAO;
import ru.vssemikoz.newsfeed.models.Category;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.storage.NewsStorage;

import static androidx.core.util.Preconditions.checkNotNull;

public class NewsFeedPresenter implements NewsFeedContract.Presenter {
    private final NewsFeedContract.View newsFeedView;

    private boolean showOnlyFavorite = false;
    private Category category = Category.ALL;
    private List<NewsItem> news;
    private MainApplication mainApplication;
    private NewsStorage newsStorage;

    public NewsFeedPresenter(NewsFeedContract.View tasksView){
        newsFeedView = checkNotNull(tasksView, "tasksView cannot be null!");
        newsFeedView.setPresenter(this);
    }
    @Override
    public void start() {

        mainApplication = (MainApplication) newsFeedView.getContext();
        loadNews();
    }

    @Override
    public void loadNews() {
        // TODO: 16.03.2020
        news = getNewsFromDB();
    }

    @Override
    public String getDisplayDescriptionText() {
        return "Категория: " + Category.getDisplayName(category);
    }

    @Override
    public void openNewsDetails(int position) {
        showNewsInBrowserByUrl(position);
    }

    @Override
    public void changeFavoriteState(int position) {
        NewsItem item = news.get(position);
        item.invertFavoriteState();
        newsStorage.updateNews(item);
        NewsFeedAdapter adapter = newsFeedView.getAdapter();
        if (!item.isFavorite() && showOnlyFavorite) {
            news.remove(position);
            adapter.notifyItemRemoved(position);
            if (news.isEmpty()) {
                newsFeedView.setEmptyViewOnDisplay();
            }
        } else {
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public void setShowFavorite(Boolean showOnlyFavorite) {
        this.showOnlyFavorite = showOnlyFavorite;
    }

    @Override
    public Boolean getShowFavorite() {
        return this.showOnlyFavorite;
    }

    private void showNewsInBrowserByUrl(int position) {
        NewsItem item = news.get(position);
        String url = item.getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        // TODO: 16.03.2020
//        try {
//            getApplicationContext().startActivity(intent);
//        } catch (ActivityNotFoundException ex) {
//            intent.setPackage(null);
//            getApplicationContext().startActivity(intent);
//        }
    }

    private List<NewsItem> getNewsFromDB() {
        return newsStorage.getNewsFromDB(showOnlyFavorite, category);
    }

    private void initNewsStorage() {
        NewsItemDAO newsItemDAO = mainApplication.getNewsDataBase().newsItemDAO();
        newsStorage = new NewsStorage(newsItemDAO);
    }
}
