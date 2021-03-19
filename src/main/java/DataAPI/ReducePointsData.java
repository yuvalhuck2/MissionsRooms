package DataAPI;

public class ReducePointsData {

    private String apiKey;
    private String alias;
    private Integer points;

    public ReducePointsData(String apiKey, String alias, Integer points) {
        this.apiKey = apiKey;
        this.alias = alias;
        this.points = points;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getAlias() {
        return alias;
    }

    public Integer getPoints() {
        return points;
    }
}
