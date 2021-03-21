package DataAPI;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;



public class ChatMessageData {

    private String messageId;

    private String message;

    private LocalDateTime dateTime;


    public ChatMessageData(String messageId,  String message,LocalDateTime dateTime) {
        this.messageId = messageId;
        this.dateTime=dateTime;
        this.message = message;
    }

    public ChatMessageData(String messageId,  String message) {
        this.messageId = messageId;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}

