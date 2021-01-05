package DataAPI;

public class CloseRoomData {
    private String apiKey;
    private String roomId;

    public CloseRoomData(String apiKey, String roomId) {
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
