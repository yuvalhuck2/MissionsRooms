package Domain;

import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Repo.MissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.persistence.*;


@Entity
@Configurable(preConstruction = true)
public class Teacher extends SchoolUser {

    @ManyToOne
    private Classroom classroom;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;

    @Autowired
    @Transient
    private MissionRepo missionRepo;


    public Teacher() {
    }

    public Teacher(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName);
        this.classroom = classroom;
        this.groupType = groupType;
    }

    public Teacher(String alias, String firstName, String lastName,String password) {
        super(alias, firstName, lastName);
    }

    public Response<Boolean> addMission(Mission mission) {
        Response<Mission> missionResponse=missionRepo.save(mission);
        if(missionResponse.getReason()!= OpCode.Success){
            return new Response<>(null,missionResponse.getReason());
        }

        return new Response<>(true,OpCode.Success);
    }
}
