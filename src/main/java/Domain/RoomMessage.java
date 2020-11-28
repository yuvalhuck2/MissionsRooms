package Domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RoomMessage {

    @Id
    private String messageKey;

    private String sender;

    private String message;
}
