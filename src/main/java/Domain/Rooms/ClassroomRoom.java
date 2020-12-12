package Domain.Rooms;

import Domain.Classroom;
import Domain.Room;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ClassroomRoom extends Room {

    @OneToOne
    private Classroom participant;

    public ClassroomRoom() {
    }
}
