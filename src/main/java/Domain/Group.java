package Domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Group extends Participant {

    @OneToMany
    private List<Student> students;

    public Group() {
    }

    public Group(String Id, List<Student> students) {
        super(Id);
        this.students = students;
    }
}
