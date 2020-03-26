package ru.vssemikoz.newsfeed;

public class AppConfig {
    private String BaseUrl;
    private String apiKey;
    private String countryKey;

    public AppConfig(String baseUrl, String apiKey, String countryKey) {
        BaseUrl = baseUrl;
        this.apiKey = apiKey;
        this.countryKey = countryKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public String getCountryKey() {
        return countryKey;
    }
}
