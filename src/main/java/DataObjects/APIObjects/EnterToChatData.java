package DataObjects.APIObjects;

public class EnterToChatData {
    private String apiKey;
    private String roomId;

    public EnterToChatData(String apiKey, String roomId) {
        this.apiKey = apiKey;
        this.roomId = roomId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getRoomId() {
        return roomId;
    }

}
