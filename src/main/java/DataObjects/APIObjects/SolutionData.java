package DataObjects.APIObjects;

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

    public String getMissionId() {
        return missionId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getOpenAnswer() {
        return openAnswer;
    }

    public Boolean getHasFile() {
        return hasFile;
    }

}
