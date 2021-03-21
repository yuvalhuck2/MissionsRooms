package DataAPI;

public class SolutionData {

    private String missionId;
    private String roomId;
    private String openAnswer;
    private Boolean hasFile;

    public SolutionData(String missionId, String roomId, String openAnswer) {
        this.missionId = missionId;
        this.roomId = roomId;
        this.openAnswer = openAnswer;
    }

    public SolutionData(String missionId, String openAnswer, boolean hasFile) {
        this.missionId = missionId;
        this.openAnswer = openAnswer;
        this.hasFile = hasFile;
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

    public Boolean getHasFile() {
        return hasFile;
    }

    public void setHasFile(Boolean hasFile) {
        this.hasFile = hasFile;
    }
}
