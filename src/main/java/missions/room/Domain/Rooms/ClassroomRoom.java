package missions.room.Domain.Rooms;

import missions.room.Domain.Classroom;
import missions.room.Domain.Room;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ClassroomRoom extends Room {

    @OneToOne
    private Classroom participant;

    public ClassroomRoom() {
    }
}
