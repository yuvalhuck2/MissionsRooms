package DataAPI;

public class SolutionData {

    private String missionId;
    private String roomId;
    private String openAnswer;

    public SolutionData(String missionId, String roomId, String openAnswer) {
        this.missionId = missionId;
        this.roomId = roomId;
        this.openAnswer = openAnswer;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(String openAnswer) {
        this.openAnswer = openAnswer;
    }
}
