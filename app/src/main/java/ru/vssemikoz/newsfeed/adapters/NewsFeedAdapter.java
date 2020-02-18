package ru.vssemikoz.newsfeed.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.NewsApiResponseItem;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder> {

    private List<NewsApiResponseItem> newsList;

    public void setNewsList(List<NewsApiResponseItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsApiResponseItem newsApiResponseItem = newsList.get(position);
        holder.title.setText(newsApiResponseItem.getTitle());
        holder.description.setText(newsApiResponseItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        final ImageView imageView;
        final TextView title;
        final TextView description;
        public NewsViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.iv_image);
            title =  view.findViewById(R.id.tv_title);
            description =  view.findViewById(R.id.tv_description);
        }
    }
}
