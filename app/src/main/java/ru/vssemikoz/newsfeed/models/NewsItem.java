package ru.vssemikoz.newsfeed.models;

import android.media.Image;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;

@Entity
public class NewsItem {
    @PrimaryKey
    private int newsId;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "url")
    private URL url;
    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    private URL imageUrl;
    @ColumnInfo(name = "published_at")
    private Date publishedAt;

    public NewsItem(String title, String description){
        this.title = title;
        this.description = description;
    }
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

    public URL getImageUrl() {
        return imageUrl;
    }

    public URL getUrl() {
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

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setImageUrl(URL imageUrl) {
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


