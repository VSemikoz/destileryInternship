package ru.vssemikoz.newsfeed.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.net.URI;
import java.util.Date;

import ru.vssemikoz.newsfeed.TypeConverters.DateConverter;
import ru.vssemikoz.newsfeed.TypeConverters.URIConverter;

@Entity(indices = @Index(value = "title", unique = true))
@TypeConverters({URIConverter.class, DateConverter.class})
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    public  int newsId;
    public  String author;
    public  String title;
    public  String description;
    public  String content;
    public  URI url;
    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    public URI imageUrl;
    @ColumnInfo(name = "published_at")
    public  Date publishedAt;

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public URI getImageUrl() {
        return imageUrl;
    }

    public URI getUrl() {
        return url;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public void setImageUrl(URI imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
}


