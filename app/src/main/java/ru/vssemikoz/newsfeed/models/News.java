package ru.vssemikoz.newsfeed.models;

import java.net.URL;
import java.util.Date;

public class News {
    private String author, title, description, content;
    private URL url, urlToImage;
    private Date publishedAt;

    News( String author, String title, String description, String content, URL url, URL urlToImage, Date publishedAt){
        this.author = author;
        this.title = title;
        this.description = description;
        this.content = content;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
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

    public URL getUrlToImage() {
        return urlToImage;
    }

    public URL getUrl() {
        return url;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }
}


