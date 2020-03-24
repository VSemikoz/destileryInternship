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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import ru.vssemikoz.newsfeed.R;
import ru.vssemikoz.newsfeed.models.NewsItem;
import ru.vssemikoz.newsfeed.data.IconicStorage;
import ru.vssemikoz.newsfeed.utils.TypeConverters.DateConverter;

public class NewsFeedAdapter extends BaseAdapter<NewsItem> {

    public interface OnNewsItemClickListener extends OnRecyclerItemClickListener {
        void onChangeFavoriteStateClick(int position);

        @Override
        void OnRecyclerItemClick(int position);
    }

    public NewsFeedAdapter(Context context) {
        super(context);
    }

    public void setOnItemClickListener(OnNewsItemClickListener mListener) {
        super.setOnItemClickListener(mListener);
    }

    @NotNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_feed_item, parent, false);
        return new NewsFeedAdapter.NewsViewHolder(view, getListener());
    }

     class NewsViewHolder extends BaseViewHolder<NewsItem> {
        boolean favoriteState;
        final CardView cardView;
        final ImageView imageView;
        final TextView title;
        final TextView description;
        final TextView dateTime;
        final TextView author;
        final ImageButton changeFavoriteStateButton;
        final ProgressBar progressBar;


        NewsViewHolder(View view, OnRecyclerItemClickListener listener) {//жду базовый
            super(view);
            OnNewsItemClickListener finalListener = (OnNewsItemClickListener) listener;
            cardView = view.findViewById(R.id.cv_item);
            imageView = view.findViewById(R.id.iv_news_image);
            title = view.findViewById(R.id.tv_title);
            description = view.findViewById(R.id.tv_description);
            dateTime = view.findViewById(R.id.et_datetime);
            author = view.findViewById(R.id.et_author);
            changeFavoriteStateButton = view.findViewById(R.id.ib_change_favorite_state);
            progressBar = view.findViewById(R.id.image_progress_bar);

            changeFavoriteStateButton.setOnClickListener(v -> {
                if (finalListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        finalListener.onChangeFavoriteStateClick(position);
                        favoriteState = !favoriteState;
                    }
                }
            });

            cardView.setOnClickListener(v -> {
                if (finalListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        finalListener.OnRecyclerItemClick(position);
                    }
                }
            });
        }

        @Override
        public void onBind(NewsItem newsItem, OnRecyclerItemClickListener listener) {
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
                                progressBar.setVisibility(ProgressBar.GONE);
                            }
                        });
            } else {
                imageView.setImageResource(R.drawable.no_image_found);
                progressBar.setVisibility(ProgressBar.GONE);
            }


        }
    }
}
