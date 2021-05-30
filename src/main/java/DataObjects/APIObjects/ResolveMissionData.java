package DataObjects.APIObjects;

public class ResolveMissionData {
    private String apiKey;
    private String roomId;
    private String missionId;
    private boolean approve;

    public ResolveMissionData(String apiKey, String roomId, String missionId, boolean approve) {
        this.apiKey = apiKey;
        this.roomId = roomId;
        this.missionId = missionId;
        this.approve = approve;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getMissionId() {
        return missionId;
    }

    public boolean isApprove() {
        return approve;
    }
}
