package Domain;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ClassGroup extends Participant {

    //one to one example
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="CLASSROOM_ALIAS")
//    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//    //@JoinColumn(name="CLASSROOM_ALIAS")
//    private Classroom classroom;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "alias")
    @JoinColumn(name="classgroup",referencedColumnName = "alias")
    private Map<String, Student> students;

    public ClassGroup() {
    }

    public ClassGroup(String Id, Map<String, Student> students) {
        super(Id);
        this.students = students;
    }
}
