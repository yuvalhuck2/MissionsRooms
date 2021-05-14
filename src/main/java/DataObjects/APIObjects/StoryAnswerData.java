package DataObjects.APIObjects;

public class StoryAnswerData {

    private String apiKey;
    private String roomId;
    private String sentence;

    public StoryAnswerData(String apiKey, String roomId, String sentence) {
        this.apiKey = apiKey;
        this.roomId = roomId;
        this.sentence = sentence;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getSentence() {
        return sentence;
    }
}
