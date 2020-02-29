package ru.vssemikoz.newsfeed.models;

import ru.vssemikoz.newsfeed.MainApplication;
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

    public static String getCategoryName(Category category) {
        return category.toString();
    }

    Category(int stringCategory) {
        this.categoryId = stringCategory;
    }

    @Override
    public String toString() {
        return MainApplication.getContext().getString(categoryId);
    }

    public static String[] getCategoryNameList() {
        Category[] categories = Category.values();
        String[] categoriesName = new String[categories.length];

        for (int i = 0; i < categories.length; i++) {
            categoriesName[i] = getCategoryName(categories[i]);
        }
        return categoriesName;
    }

}
