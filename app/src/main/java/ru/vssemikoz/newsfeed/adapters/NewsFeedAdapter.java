package ru.vssemikoz.newsfeed.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.NewsItem;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder> {

    private List<NewsItem> newsList;
    private Context context;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onFollowClick(int position);
    }

    public void  setOnItemClickListener(onItemClickListener listener){
        this.mListener = listener;
    }

    public NewsFeedAdapter(Context context){
        this.context = context;
    }

    public void setNewsList(List<NewsItem> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsFeedAdapter.NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsApiResponseItem = newsList.get(position);
        holder.title.setText(newsApiResponseItem.getTitle());
        holder.description.setText(newsApiResponseItem.getDescription());
        holder.followState = newsApiResponseItem.isFavorite();

        if (holder.followState){
            holder.followButton.setImageResource(R.drawable.ic_favorite_red_48dp);
        }else {
            holder.followButton.setImageResource(R.drawable.ic_favorite_white_48dp);
        }

        if (newsApiResponseItem.getImageUrl() != null){
            Picasso.with(context)
                    .load(newsApiResponseItem.getImageUrl())
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        boolean followState;

        final ImageView imageView;
        final TextView title;
        final TextView description;
        final ImageButton followButton;

        public NewsViewHolder(View view, onItemClickListener listener) {
            super(view);
            imageView = view.findViewById(R.id.iv_image);
            title =  view.findViewById(R.id.tv_title);
            description =  view.findViewById(R.id.tv_description);
            followButton = view.findViewById(R.id.ib_follow);

            followButton.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onFollowClick(position);
                        followState = !followState;
                    }
                }

            });
        }
    }
}
