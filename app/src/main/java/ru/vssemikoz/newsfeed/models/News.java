package ru.vssemikoz.newsfeed.models;

import java.net.URL;
import java.util.Date;

public class News {
    private String author;
    private String title;
    private String description;
    private String content;
    private URL url;
    private URL urlToImage;
    private Date publishedAt;


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

    public URL getUrlToImage() {
        return urlToImage;
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

    public void setUrlToImage(URL urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}


