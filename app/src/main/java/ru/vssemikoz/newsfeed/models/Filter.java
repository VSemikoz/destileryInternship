package ru.vssemikoz.newsfeed.models;

public class Filter {
    private Category category;
    private ShowOnlyFavorite showOnlyFavorite;

    public Filter(Category category, ShowOnlyFavorite showOnlyFavorite) {
        this.category = category;
        this.showOnlyFavorite = showOnlyFavorite;
    }

    public Category getCategory() {
        return category;
    }

    public ShowOnlyFavorite getShowOnlyFavorite() {
        return showOnlyFavorite;
    }
}
