package missions.room.Domain.Rooms;

import missions.room.Domain.Classroom;
import missions.room.Domain.Room;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ClassroomRoom extends Room {

    @OneToOne
    private Classroom participant;

    public ClassroomRoom() {
    }

    @Override
    protected String drawMissionInCharge() {
        return null;
    }

    public ClassroomRoom(String roomId, String roomName, Classroom participant, Teacher teacher, RoomTemplate roomTemplate,int bonus) {
        super(roomId,roomName,teacher,roomTemplate,bonus);
        this.participant = participant;
    }
}
