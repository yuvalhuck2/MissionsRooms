package DataAPI;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;



public class ChatMessageData {

    private String text;
    private UserChatData user;

    private String createdAt;
    private String _id;

    public ChatMessageData(String text, UserChatData user, String dateTime, String messageId) {
        this.text = text;
        this.user = user;
        this.createdAt = dateTime;
        this._id = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserChatData getUser() {
        return user;
    }

    public void setUser(UserChatData user) {
        this.user = user;
    }

    public String getDateTime() {
        return createdAt;
    }

    public void setDateTime(String dateTime) {
        this.createdAt = dateTime;
    }

    public String get_id() {
        return _id;
    }

}

