package DataAPI;

public class CreateMissionData {
    private String apiKey;
    private String missionData;

    public CreateMissionData(String apiKey, String missionData) {
        this.apiKey = apiKey;
        this.missionData = missionData;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getMissionData() {
        return missionData;
    }
}
