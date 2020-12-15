package missions.room.Domain.Rooms;

import missions.room.Domain.Room;
import missions.room.Domain.Student;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class StudentRoom extends Room {

    @OneToOne
    private Student participant;

    public StudentRoom() {
    }


}
