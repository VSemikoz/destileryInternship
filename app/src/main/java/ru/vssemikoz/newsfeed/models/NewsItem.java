package ru.vssemikoz.newsfeed.models;

import java.util.Date;

public class NewsItem {
    private String author;
    private String title;
    private String description;
    private String content;
    private String url;
    private String imageUrl;
    private Date publishedAt;

    NewsItem(){

    }

    NewsItem(NewsApiResponseItem newsApiResponseItem){
        this.author = newsApiResponseItem.getAuthor();
        this.title = newsApiResponseItem.getTitle();
        this.description = newsApiResponseItem.getDescription();
        this.content = newsApiResponseItem.getContent();
        this.url = newsApiResponseItem.getUrl();
        this.imageUrl = newsApiResponseItem.getImageUrl();
        this.publishedAt = newsApiResponseItem.getPublishedAt();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
}
