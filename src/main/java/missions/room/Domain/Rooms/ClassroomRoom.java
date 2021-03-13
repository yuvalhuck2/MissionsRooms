package missions.room.Domain.Rooms;

import DataAPI.GroupType;
import DataAPI.RoomType;
import missions.room.Domain.*;
import missions.room.Domain.Users.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ClassroomRoom extends Room {

    @OneToOne
    private Classroom participant;

    public ClassroomRoom() {
    }

    @Override
    protected RoomType addPoints(int points) {
        participant.addPoints(points);
        if(toCloseRoom()&&roomTemplate.getMinimalMissionsToPass()<=countCorrectAnswer){
            participant.addPoints(bonus);
        }
        return RoomType.Class;
    }

    @Override
    public boolean isBelongToRoom(String alias) {
        return participant.getStudent(alias, GroupType.A)!=null||
                participant.getStudent(alias,GroupType.B)!=null;
    }

    @Override
    protected int getParticipantsSize() {
        return participant.getStudentsAlias().size();
    }

    public ClassroomRoom(String roomId, String roomName, Classroom participant, Teacher teacher, RoomTemplate roomTemplate, int bonus) {
        super(roomId,roomName,teacher,roomTemplate,bonus);
        this.participant = participant;
    }

    public Classroom getParticipant() {
        return participant;
    }
}
