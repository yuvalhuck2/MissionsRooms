package DataAPI;

public class Auth {
    private String apiKey;

    public Auth(String phoneNumber, String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
