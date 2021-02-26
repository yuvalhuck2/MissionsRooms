package missions.room.Domain.Rooms;

import DataAPI.RoomType;
import missions.room.Domain.Room;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Student;
import missions.room.Domain.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class StudentRoom extends Room {

    @OneToOne
    private Student participant;

    public StudentRoom() {
    }

    @Override
    public String drawMissionInCharge() {
        return participant.getAlias();
    }

    @Override
    protected RoomType addPoints(int points) {
        participant.addPoints(points);
        if(toCloseRoom()&&roomTemplate.getMinimalMissionsToPass()<=countCorrectAnswer){
            participant.addPoints(bonus);
        }
        return RoomType.Personal;
    }

    @Override
    public boolean isBelongToRoom(String alias) {
        return alias.equals(participant.getAlias());
    }

    public StudentRoom(String roomId,String name, Student participant, Teacher teacher, RoomTemplate roomTemplate,int bonus) {
        super(roomId,name,teacher,roomTemplate,bonus);
        this.participant = participant;
    }

    public Student getParticipant() {
        return participant;
    }
}
