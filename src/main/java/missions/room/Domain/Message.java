package missions.room.Domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Message {

    @Id
    private String id;

    private String sender;

    private String content;


    public Message() {
    }

    public Message(String id, String sender, String content) {
        this.id = id;
        this.sender = sender;
        this.content = content;
    }


}
