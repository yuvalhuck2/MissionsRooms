package DataAPI;

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

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
