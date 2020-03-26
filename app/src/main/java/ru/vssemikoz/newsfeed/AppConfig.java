package ru.vssemikoz.newsfeed;

public class AppConfig {
    private String BaseUrl;
    private String apiKey;

    public AppConfig(String apiBaseUrl, String apiKey) {
        this.BaseUrl = apiBaseUrl;
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }
}
