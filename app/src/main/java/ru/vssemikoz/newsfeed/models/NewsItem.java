package ru.vssemikoz.newsfeed.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import ru.vssemikoz.newsfeed.utils.TypeConverters.DateConverter;

@Entity(primaryKeys = {"title", "url"})
@TypeConverters({DateConverter.class})
public class NewsItem implements Parcelable {

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;
    private String category;
    private String author;
    @NotNull
    private String title;
    private String description;
    private String content;
    @NotNull
    private String url;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "published_at")
    private Date publishedAt;

    public NewsItem() {

    }

    public NewsItem(NewsApiResponseItem newsApiResponseItem, Category category) {
        this.isFavorite = false;
        this.author = newsApiResponseItem.getAuthor();
        this.title = newsApiResponseItem.getTitle();
        this.description = newsApiResponseItem.getDescription();
        this.content = newsApiResponseItem.getContent();
        this.url = newsApiResponseItem.getUrl();
        this.imageUrl = newsApiResponseItem.getImageUrl();
        this.publishedAt = DateConverter.fromString(newsApiResponseItem.getPublishedAt());

        if (category == null) {
            this.category = "";
        } else {
            this.category = category.name();
        }
    }

    protected NewsItem(Parcel in) {
        isFavorite = in.readByte() != 0;
        category = in.readString();
        author = in.readString();
        title = in.readString();
        description = in.readString();
        content = in.readString();
        url = in.readString();
        imageUrl = in.readString();
        publishedAt = DateConverter.fromString(in.readString());
    }

    public static final Creator<NewsItem> CREATOR = new Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };

    public void invertFavoriteState() {
        isFavorite = !isFavorite;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString((String.valueOf(isFavorite)));
        dest.writeString(category);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(content);
        dest.writeString(url);
        dest.writeString(imageUrl);
        dest.writeString(DateConverter.fromDate(publishedAt));

    }
}
