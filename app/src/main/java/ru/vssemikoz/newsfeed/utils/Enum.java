package ru.vssemikoz.newsfeed.utils;

import ru.vssemikoz.newsfeed.models.Category;

public class Enum {

    public static String[] getCategoryNameList(){
        Category[] states = Category.values();
        String names[] = new String[states.length];

        for (int i = 0; i < states.length; i++) {
            names[i] = states[i].getStrName();
        }
        return names;
    }

}
