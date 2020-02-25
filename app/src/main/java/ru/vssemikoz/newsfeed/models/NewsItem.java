package ru.vssemikoz.newsfeed.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import ru.vssemikoz.newsfeed.utils.TypeConverters.DateConverter;

@Entity(indices = @Index(value = "title", unique = true))
@TypeConverters({DateConverter.class})
public class NewsItem {
    @PrimaryKey(autoGenerate = true)
    private int newsId;
    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;
    private String category;
    private String author;
    private String title;
    private String description;
    private String content;
    private String url;
    @ColumnInfo(name = "image_url")
    private String imageUrl;
    @ColumnInfo(name = "published_at")
    private Date publishedAt;

    public NewsItem(){

    }

    public NewsItem(NewsApiResponseItem newsApiResponseItem, Category category){
        this.isFavorite = false;
        this.author = newsApiResponseItem.getAuthor();
        this.title = newsApiResponseItem.getTitle();
        this.description = newsApiResponseItem.getDescription();
        this.content = newsApiResponseItem.getContent();
        this.url = newsApiResponseItem.getUrl();
        this.imageUrl = newsApiResponseItem.getImageUrl();
        this.publishedAt = DateConverter.fromString(newsApiResponseItem.getPublishedAt());

        if (category == null){
            this.category = "";
        }else {
            this.category = category.name();
        }
    }

    public void invertFollowState(){
        isFavorite = !isFavorite;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
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
}
