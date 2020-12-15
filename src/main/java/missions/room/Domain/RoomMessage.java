package missions.room.Domain;

import javax.persistence.*;

@Entity
public class RoomMessage {

    @Id
    private String messageKey;

    private String sender;

    private String message;

    public RoomMessage() {
    }

    public RoomMessage(String messageKey, String sender, String message) {
        this.messageKey = messageKey;
        this.sender = sender;
        this.message = message;
    }
}
