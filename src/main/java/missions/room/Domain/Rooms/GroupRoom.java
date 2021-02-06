package missions.room.Domain.Rooms;

import missions.room.Domain.ClassGroup;
import missions.room.Domain.Room;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class GroupRoom extends Room {

    @OneToOne
    private ClassGroup participant;

    public GroupRoom() {
    }

    @Override
    protected String drawMissionInCharge() {
        return null;
    }

    public GroupRoom(String roomId, String roomName, ClassGroup participant, Teacher teacher, RoomTemplate roomTemplate,int bonus) {
        super(roomId,roomName,teacher,roomTemplate,bonus);
        this.participant = participant;
    }

    public ClassGroup getParticipant() {
        return participant;
    }
}
