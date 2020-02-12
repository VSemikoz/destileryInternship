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
import ru.vssemikoz.newsfeed.models.Filter;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;

    public NewsFeedAdapter(Filter filter, List<NewsItem> newsList){
        this.newsList = newsList;
    }
    @NonNull
    @Override
    public NewsFeedAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = newsList.get(position);

//        holder.imageView.setImageResource(newsItem.getImageUrl());

        holder.title.setText(newsItem.getTitle());
        holder.description.setText(newsItem.getDescription());
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
