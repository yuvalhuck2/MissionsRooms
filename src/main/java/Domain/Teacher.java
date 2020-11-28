package Domain;

import javax.persistence.*;

@Entity
public class Teacher extends Student {

    @ManyToOne
    private Classroom classroom;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;

    public Teacher() {
    }

    public Teacher(String alias, String firstName, String lastName, String phoneNumber, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName, phoneNumber);
        this.classroom = classroom;
        this.groupType = groupType;
    }


}
