package missions.room.Domain.Users;

import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.GroupType;
import DataObjects.FlatDataObjects.OpCode;
import missions.room.Domain.Classroom;

import javax.persistence.Entity;

@Entity
public class Supervisor extends Teacher {

    public Supervisor() {
        super();
    }

    public Supervisor(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName,classroom,groupType);
    }

    public Supervisor(TeacherData profileDetails) {
        super(profileDetails);
    }

    @Override
    public boolean isSupervisor() {
        return true;
    }

    @Override
    public OpCode getOpcode() {
        return OpCode.Supervisor;
    }
}
