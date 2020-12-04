package DataAPI;

public class Auth {
    private String phoneNumber;
    private String apiKey;

    public Auth(String phoneNumber, String apiKey) {
        this.phoneNumber = phoneNumber;
        this.apiKey = apiKey;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getApiKey() {
        return apiKey;
    }
}
