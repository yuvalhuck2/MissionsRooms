package Domain;

import DataAPI.RoomType;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    private String roomId;

    private RoomType roomType;

    @OneToMany
    private List<RoomMessage> roomMessages;

    @OneToOne
    private Participant participant;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private RoomTemplate roomTemplate;

    public Room() {
    }

    public Room(String roomId) {
        this.roomId = roomId;
    }
}
