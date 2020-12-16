package DataAPI;

public class newRoomDetails {

    private String templateId;
    private String participantKey;
    private RoomType roomType;
    private String roomName;

    public newRoomDetails(String participantKey, RoomType roomType, String roomName) {
        this.participantKey = participantKey;
        this.roomType = roomType;
        this.roomName = roomName;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }


    public String getTemplateId() {
        return templateId;
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
}
