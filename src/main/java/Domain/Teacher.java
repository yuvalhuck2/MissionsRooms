package Domain;

import javax.persistence.*;

@Entity
public class Teacher extends User {

    @ManyToOne
    private Classroom classroom;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;

    public Teacher() {
    }

    public Teacher(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName);
        this.classroom = classroom;
        this.groupType = groupType;
    }


}
