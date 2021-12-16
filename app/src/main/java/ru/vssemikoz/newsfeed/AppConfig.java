package ru.vssemikoz.newsfeed;

public class AppConfig {
    private final String baseUrl;
    private final String apiKey;
    private final String countryKey;

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
