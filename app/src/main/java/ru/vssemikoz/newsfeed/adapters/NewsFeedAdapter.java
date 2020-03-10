package ru.vssemikoz.newsfeed.adapters;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.storage.IconicStorage;
import ru.vssemikoz.newsfeed.utils.TypeConverters.DateConverter;

public class NewsFeedAdapter extends BaseAdapter {
    private onNewsItemClickListener listener;

    public interface onNewsItemClickListener extends OnRecyclerItemClickListener {
        void onChangeFavoriteStateClick(int position);

        void onNewsImageClick(int position);
    }

    public NewsFeedAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(onNewsItemClickListener mListener) {
        super.setOnItemClickListener(mListener);
        this.listener = mListener;
    }

    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsFeedAdapter.NewsViewHolder(view, listener);
    }

    public class NewsViewHolder extends BaseHolder {
        boolean favoriteState;
        final ImageView imageView;
        final TextView title;
        final TextView description;
        final TextView dateTime;
        final TextView author;
        final ImageButton changeFavoriteStateButton;
        final ProgressBar progressBar;


        NewsViewHolder(View view, onNewsItemClickListener listener) {
            super(view);
            imageView = view.findViewById(R.id.iv_news_image);
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);
            dateTime = view.findViewById(R.id.et_datetime);
            author = view.findViewById(R.id.et_author);
            changeFavoriteStateButton = view.findViewById(R.id.ib_change_favorite_state);
            progressBar = view.findViewById(R.id.image_progress_bar);

            changeFavoriteStateButton.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onChangeFavoriteStateClick(position);
                        favoriteState = !favoriteState;
                    }
                }
            });

            imageView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onNewsImageClick(position);
                    }
                }
            });
        }

        @Override
        public void onBind(Object item, OnRecyclerItemClickListener listener) {
            super.onBind(item, listener);
            NewsItem newsItem = (NewsItem) item;
            title.setText(newsItem.getTitle());
            description.setText(newsItem.getDescription());
            favoriteState = newsItem.isFavorite();
            dateTime.setText(DateConverter.fromDateToUIFormat(newsItem.getPublishedAt()));
            author.setText(newsItem.getAuthor());

            if (favoriteState) {
                changeFavoriteStateButton.setImageDrawable(IconicStorage.getYellowStarBorder(getContext()));
            } else {
                changeFavoriteStateButton.setImageDrawable(IconicStorage.getWhiteStarBorder(getContext()));
            }

            progressBar.setVisibility(ProgressBar.VISIBLE);
            if (!TextUtils.isEmpty(newsItem.getImageUrl())) {
                Picasso.with(getContext())
                        .load(newsItem.getImageUrl())
                        .error(R.drawable.no_image_found)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(ProgressBar.GONE);
                            }

                            @Override
                            public void onError() {
                            }
                        });
            } else {
                imageView.setImageResource(R.drawable.no_image_found);
                progressBar.setVisibility(ProgressBar.GONE);
            }


        }
    }

}
