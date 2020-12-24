package missions.room.Domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Room {

    @Id
    protected String roomId;

    protected String name;

    protected int bonus;

    protected int currentMission;

    @Transient
    protected Set<String>  studentWereChosen;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="roomId",referencedColumnName = "roomId")
    protected List<RoomMessage> roomMessages;

    @OneToOne
    protected Teacher teacher;

    @OneToOne
    protected RoomTemplate roomTemplate;

    public Room() {
        studentWereChosen=new HashSet<>();
    }

    public Room(String roomId,String name,Teacher teacher,RoomTemplate roomTemplate,int bonus) {
        this.roomId = roomId;
        this.name=name;
        this.teacher=teacher;
        this.roomTemplate=roomTemplate;
        studentWereChosen=new HashSet<>();
        this.bonus=bonus;
        this.currentMission=0;
    }
     protected abstract String drawMissionInCharge();

    public String getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public int getBonus() {
        return bonus;
    }

    public int getCurrentMission() {
        return currentMission;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public RoomTemplate getRoomTemplate() {
        return roomTemplate;
    }
}
