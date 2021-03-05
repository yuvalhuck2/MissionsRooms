package missions.room.Domain.Rooms;

import DataAPI.RoomType;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Users.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class GroupRoom extends Room {

    @OneToOne
    private ClassGroup participant;

    public GroupRoom() {
    }

    @Override
    protected RoomType addPoints(int points) {
        participant.addPoints(points);
        if(toCloseRoom()&&roomTemplate.getMinimalMissionsToPass()<=countCorrectAnswer){
            participant.addPoints(bonus);
        }
        return RoomType.Group;
    }

    @Override
    public boolean isBelongToRoom(String alias) {
        return participant.containsStudent(alias);
    }

    @Override
    protected int getParticipantsSize() {
        return participant.getStudent().size();
    }

    public GroupRoom(String roomId, String roomName, ClassGroup participant, Teacher teacher, RoomTemplate roomTemplate,int bonus) {
        super(roomId,roomName,teacher,roomTemplate,bonus);
        this.participant = participant;
    }

    public ClassGroup getParticipant() {
        return participant;
    }
}
