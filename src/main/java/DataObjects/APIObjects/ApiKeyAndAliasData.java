package DataObjects.APIObjects;

public class ApiKeyAndAliasData {
    private String apiKey;
    private String alias;

    public ApiKeyAndAliasData(String apiKey, String alias) {
        this.apiKey = apiKey;
        this.alias = alias;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAlias() {
        return alias;
    }

}
