package DataObjects.APIObjects;

public class MessageChatDetails {
    private String apiKey;
    private ChatMessageData newMessage;
    private String roomId;

    public MessageChatDetails(String apiKey, ChatMessageData newMessage, String roomId) {
        this.apiKey = apiKey;
        this.newMessage = newMessage;
        this.roomId = roomId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ChatMessageData getChatMessageData() {
        return newMessage;
    }

    public String getRoomId() {
        return roomId;
    }

}
