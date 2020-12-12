package Domain.Rooms;

import Domain.ClassGroup;
import Domain.Room;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class GroupRoom extends Room {

    @OneToOne
    private ClassGroup participant;

    public GroupRoom() {
    }
}
