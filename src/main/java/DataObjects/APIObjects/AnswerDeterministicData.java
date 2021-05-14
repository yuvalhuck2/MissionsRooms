package DataObjects.APIObjects;

public class AnswerDeterministicData {
    private String apiKey;
    private String roomId;
    private boolean answer;

    public AnswerDeterministicData(String apiKey, String roomId, boolean answer) {
        this.apiKey = apiKey;
        this.roomId = roomId;
        this.answer = answer;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isAnswer() {
        return answer;
    }
}
