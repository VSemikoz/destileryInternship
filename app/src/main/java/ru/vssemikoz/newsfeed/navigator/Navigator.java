package ru.vssemikoz.newsfeed.navigator;

public interface Navigator {

    void openWebView(String url);

    void sendPlainTextToApps(String text);
}
