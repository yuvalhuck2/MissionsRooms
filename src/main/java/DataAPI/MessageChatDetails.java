package DataAPI;

public class MessageChatDetails {
    private String apiKey;
    private ChatMessageData chatMessageData;
    private String roomId;

    public MessageChatDetails(String apiKey, ChatMessageData chatMessageData, String roomId) {
        this.apiKey = apiKey;
        this.chatMessageData = chatMessageData;
        this.roomId = roomId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ChatMessageData getChatMessageData() {
        return chatMessageData;
    }

    public void setChatMessageData(ChatMessageData chatMessageData) {
        this.chatMessageData = chatMessageData;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
