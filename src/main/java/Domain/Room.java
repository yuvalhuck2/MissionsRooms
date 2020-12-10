package Domain;

import DataAPI.RoomType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    private String roomId;

    @Enumerated(EnumType.ORDINAL)
    private RoomType roomType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="roomId",referencedColumnName = "roomId")
    private List<RoomMessage> roomMessages;

    @OneToOne
    private Participant participant;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private RoomTemplate roomTemplate;

    public Room() {
    }

    //TODO fix constructor
    public Room(String roomId) {
        this.roomId = roomId;
    }
}
