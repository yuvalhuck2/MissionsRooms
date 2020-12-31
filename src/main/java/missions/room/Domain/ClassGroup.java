package missions.room.Domain;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Map;

@Entity
public class ClassGroup {

    @Id
    private String groupName;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey
    @JoinColumn(name="classGroup",referencedColumnName = "groupName")
    private Map<String, Student> students;

    public ClassGroup() {
    }

    public ClassGroup(String id,GroupType groupType, Map<String, Student> students) {
        this.groupName=id;
        this.students = students;
        this.groupType = groupType;
    }

    public Student getStudent(String alias,GroupType groupType) {
        if(this.groupType==groupType||groupType==GroupType.BOTH)
            return students.get(alias);
        return null;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    @Transactional
    public void addStudent(Student student) {
        students.put(student.getAlias(),student);
    }

    @Transactional
    public Student removeStudent(String studentAlias) {
        Student student=students.get(studentAlias);
        students.remove(studentAlias);
        return student;
    }
}
