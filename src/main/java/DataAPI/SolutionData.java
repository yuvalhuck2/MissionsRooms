package DataAPI;

public class SolutionData {

    private String missionId;
    private String roomId;
    private String openAnswer;
    private Boolean hasFile;
    private String missionQuestion;
    private String fileName;

    public SolutionData(String missionId, String roomId, String openAnswer) {
        this.missionId = missionId;
        this.roomId = roomId;
        this.openAnswer = openAnswer;
        this.fileName = null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public SolutionData(String missionId, String openAnswer, boolean hasFile, String missionQuestion) {
        this.missionId = missionId;
        this.openAnswer = openAnswer;
        this.hasFile = hasFile;
        this.missionQuestion = missionQuestion;
    }

    public String getMissionQuestion() {
        return missionQuestion;
    }

    public void setMissionQuestion(String missionQuestion) {
        this.missionQuestion = missionQuestion;
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
