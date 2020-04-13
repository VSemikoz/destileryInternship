package ru.vssemikoz.newsfeed.models;

import android.content.Context;

import java.util.List;
import ru.vssemikoz.newsfeed.R;

public enum Category {

    ALL(R.string.category_all),
    BUSINESS(R.string.category_business),
    ENTERTAINMENT(R.string.category_entertainment),
    HEALTH(R.string.category_health),
    SCIENCE(R.string.category_science),
    SPORTS(R.string.category_sport),
    TECHNOLOGY(R.string.category_technology);

    private Integer categoryId;
    private String categoryName;

    public void setCategoryName(Context context) {
        if (this == Category.ALL) {
            this.categoryName = context.getString(R.string.display_category_no_limits);
        } else {
            this.categoryName = context.getString(categoryId);
        }
    }

    public static String getCategoryName(Category category) {
        return category.name();
    }

    public static String getDisplayName(Category category) {
        return category.categoryName;
    }

    Category(int categoryResId) {
        this.categoryId = categoryResId;
    }

    public static String[] getCategoryNameList() {
        Category[] values = Category.values();
        String[] categories = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            categories[i] = getDisplayName(values[i]);
        }
        return categories;
    }

    public static void resolveCategory(Context context, List<Category> categories) {
        for (Category category : categories) {
            category.setCategoryName(context);
        }
    }

}
