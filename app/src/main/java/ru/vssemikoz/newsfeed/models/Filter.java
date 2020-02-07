package ru.vssemikoz.newsfeed.models;

public class Filter {
    private String filterName;

    Filter(String filterName){
        this.filterName = filterName;
    }

    public String getFilterName() {
        return filterName;
    }


}
