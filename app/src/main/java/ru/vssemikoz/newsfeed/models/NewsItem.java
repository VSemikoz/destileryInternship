package ru.vssemikoz.newsfeed.models;

import android.media.Image;

import java.net.URL;
import java.util.Date;

public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String content;
    private URL url;
    private URL imageUrl;
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

}


