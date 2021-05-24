package DataObjects.APIObjects;

public class RoomIdAndApiKeyData {

    private String apiKey;
    private String roomId;

    public RoomIdAndApiKeyData(String apiKey, String roomId) {
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
