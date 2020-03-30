package ru.vssemikoz.newsfeed;

public class AppConfig {
    private String baseUrl;
    private String apiKey;
    private String countryKey;

    public AppConfig(String baseUrl, String apiKey, String countryKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.countryKey = countryKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getCountryKey() {
        return countryKey;
    }
}
