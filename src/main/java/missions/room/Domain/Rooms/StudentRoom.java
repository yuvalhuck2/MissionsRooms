package missions.room.Domain.Rooms;

import missions.room.Domain.Room;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Student;
import missions.room.Domain.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class StudentRoom extends Room {

    @OneToOne
    private Student participant;

    public StudentRoom() {
    }

    @Override
    protected String drawMissionInCharge() {
        return null;
    }

    public StudentRoom(String roomId,String name, Student participant, Teacher teacher, RoomTemplate roomTemplate,int bonus) {
        super(roomId,name,teacher,roomTemplate,bonus);
        this.participant = participant;
    }
}
