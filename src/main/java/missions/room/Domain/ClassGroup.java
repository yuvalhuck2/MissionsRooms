package missions.room.Domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ClassGroup {

    @Id
    private String groupName;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;

    @LazyCollection(LazyCollectionOption.FALSE)
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
}
