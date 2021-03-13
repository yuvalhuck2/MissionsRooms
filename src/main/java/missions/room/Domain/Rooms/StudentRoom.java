package missions.room.Domain.Rooms;

import DataAPI.RoomType;
import missions.room.Domain.RoomTemplate;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.Teacher;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class StudentRoom extends Room {

    @OneToOne
    private Student participant;

    public StudentRoom() {
    }

    @Override
    public String drawMissionInCharge() {
        missionIncharge=participant.getAlias();;
        return missionIncharge;
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

    @Override
    protected int getParticipantsSize() {
        return 1;
    }

    public StudentRoom(String roomId,String name, Student participant, Teacher teacher, RoomTemplate roomTemplate,int bonus) {
        super(roomId,name,teacher,roomTemplate,bonus);
        this.participant = participant;
    }

    public Student getParticipant() {
        return participant;
    }
}
