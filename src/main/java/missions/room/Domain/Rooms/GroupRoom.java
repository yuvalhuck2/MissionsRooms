package missions.room.Domain.Rooms;

import missions.room.Domain.ClassGroup;
import missions.room.Domain.Room;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class GroupRoom extends Room {

    @OneToOne
    private ClassGroup participant;

    public GroupRoom() {
    }
}
