package missions.room.Domain;

import javax.persistence.Entity;

@Entity
public class Supervisor extends Teacher {

    public Supervisor() {
    }

    public Supervisor(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName,classroom,groupType);
    }

    @Override
    public boolean isSupervisor() {
        return true;
    }
}
