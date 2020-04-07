package ru.vssemikoz.newsfeed.models;

public enum ShowOnlyFavorite {
    SHOW,
    NOT_SHOW;

    public ShowOnlyFavorite invertState(){
        switch (this){
            case SHOW: return NOT_SHOW;
            case NOT_SHOW: return SHOW;
        }
        throw new AssertionError("Unknown ShowOnlyFavorite " + this);

    }

    public Boolean isShow(){
        switch (this){
            case SHOW: return true;
            case NOT_SHOW: return false;
        }
        throw new AssertionError("Unknown ShowOnlyFavorite " + this);
    }
}


