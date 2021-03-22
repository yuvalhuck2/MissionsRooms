package DataAPI;

import java.util.List;

public class RoomOpenAnswerData {
    private String roomId;
    private String roomName;
    private List<SolutionData> openAnswers;

    public RoomOpenAnswerData(String roomId, String roomName, List<SolutionData> openAnswers) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.openAnswers = openAnswers;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<SolutionData> getOpenAnswers() {
        return openAnswers;
    }

    public void setOpenAnswers(List<SolutionData> openAnswers) {
        this.openAnswers = openAnswers;
    }
}
