package ru.vssemikoz.newsfeed.models;

public class Filter {
    private Category category;
    private Boolean showOnlyFavorite;

    public Filter(Category category, Boolean showOnlyFavorite) {
        this.category = category;
        this.showOnlyFavorite = showOnlyFavorite;
    }

    public Category getCategory() {
        return category;
    }

    public Boolean getShowOnlyFavorite() {
        return showOnlyFavorite;
    }
}
