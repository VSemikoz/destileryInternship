package ru.vssemikoz.newsfeed.models;

public enum Category {
    all("Все"),
    business("Бизнес"),
    entertainment("Развлечения"),
    health("Здоровье"),
    science("Наука"),
    sport("Спорт"),
    technology("Технологии");

    private String strName;
    Category(String stringCategory){
        this.strName = stringCategory;
    }

    public String getStrName() {
        return strName;
    }
}
