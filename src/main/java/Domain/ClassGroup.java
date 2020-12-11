package Domain;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ClassGroup {

    @Id
    private String groupName;

    //one to one example
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="CLASSROOM_ALIAS")
//    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//    //@JoinColumn(name="CLASSROOM_ALIAS")
//    private Classroom classroom;

    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name = "alias")
    @JoinColumn(name="classgroup",referencedColumnName = "groupName")
    private Map<String, Student> students;

    public ClassGroup() {
    }

    public ClassGroup(String id, Map<String, Student> students) {
        this.groupName=id;
        this.students = students;
    }
}
