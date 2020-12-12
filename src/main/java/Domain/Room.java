package Domain;

import DataAPI.RoomType;
import Domain.RoomMessage;
import Domain.RoomTemplate;
import Domain.Teacher;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Room {

    @Id
    protected String roomId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="roomId",referencedColumnName = "roomId")
    protected List<RoomMessage> roomMessages;

    @OneToOne
    protected Teacher teacher;

    @OneToOne
    protected RoomTemplate roomTemplate;

    public Room() {
    }

    //TODO fix constructor
    public Room(String roomId) {
        this.roomId = roomId;
    }
}
