package DataAPI;

/**
 * user type - enum that tell if it is student, teacher, supervisor or IT
 */
public class RegisterDetailsData {
    private final String alias;
    private String password;
    private String apiKey;

    public RegisterDetailsData(String alias, String password) {
        this.alias = alias;
        this.password = password;
    }


    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }
}
