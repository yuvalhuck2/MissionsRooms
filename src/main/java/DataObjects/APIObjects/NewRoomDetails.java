package DataObjects.APIObjects;

import DataObjects.FlatDataObjects.RoomType;

public class NewRoomDetails {


    private String apiKey;
    private String roomId;
    private String roomTemplateId;
    private String participantKey;
    private RoomType roomType;
    private String roomName;
    private int bonus;

    public NewRoomDetails(String participantKey, RoomType roomType, String roomName, String roomTemplateId, int bonus) {
        this.participantKey = participantKey;
        this.roomType = roomType;
        this.roomName = roomName;
        this.roomTemplateId=roomTemplateId;
        this.bonus=bonus;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public String getRoomId() {
        return roomId;
    }

    public String getParticipantKey() {
        return participantKey;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomTemplateId() {
        return roomTemplateId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getBonus() {
        return bonus;
    }
}
