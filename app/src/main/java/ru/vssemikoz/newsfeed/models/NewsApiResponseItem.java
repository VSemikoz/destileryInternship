package ru.vssemikoz.newsfeed.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewsApiResponseItem {
    private String author;
    private String title;
    private String description;
    private String content;
    private String url;
    @SerializedName("urlToImage")
    private String imageUrl;
    private String publishedAt;

    public NewsApiResponseItem(){

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

    public String getUrl() {
        return url;
    }

    public String getPublishedAt() {
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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

}


