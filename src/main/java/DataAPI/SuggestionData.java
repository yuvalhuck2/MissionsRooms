package DataAPI;

import java.util.Objects;

public class SuggestionData {
    private String apiKey;
    private String suggestion;
    private String id;

    public SuggestionData(String apiKey, String suggestion, String id) {
        this.apiKey = apiKey;
        this.suggestion = suggestion;
        this.id=id;
    }

    public SuggestionData(String id, String suggestion) {
        this.id=id;
        this.suggestion=suggestion;
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

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuggestionData that = (SuggestionData) o;
        return suggestion.equals(that.suggestion);
    }
    
}
