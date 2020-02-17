package ru.vssemikoz.newsfeed.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;

import ru.vssemikoz.newsfeed.TypeConverters.DateConverter;
import ru.vssemikoz.newsfeed.TypeConverters.UrlConverter;

@Entity(indices = @Index(value = "title", unique = true))
@TypeConverters({UrlConverter.class, DateConverter.class})
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    public  int newsId;
    public  String author;
    public  String title;
    public  String description;
    public  String content;
    public  URL url;
    @ColumnInfo(name = "image_url")
    @SerializedName("urlToImage")
    public  String imageUrl;//Cant define type URL cause api callback return "no protocol"
    @ColumnInfo(name = "published_at")
    public  Date publishedAt;

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

    public String getImageUrl() {
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

    public void setImageUrl(String imageUrl) {
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


