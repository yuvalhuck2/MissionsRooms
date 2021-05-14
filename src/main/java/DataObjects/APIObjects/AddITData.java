package DataObjects.APIObjects;

public class AddITData {

    private final String apiKey;
    private final String alias;
    private final String password;

    public AddITData(String apiKey, String alias, String password) {
        this.apiKey = apiKey;
        this.alias = alias;
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }
}
