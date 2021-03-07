package DataAPI;

public class SuggestionData {
    private String apiKey;
    private String suggestion;

    public SuggestionData(String apiKey, String suggestion) {
        this.apiKey = apiKey;
        this.suggestion = suggestion;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
