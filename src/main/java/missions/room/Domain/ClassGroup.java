package missions.room.Domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Map;

@Entity
public class ClassGroup {

    @Id
    @Column(nullable = true)
    private String groupName;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey
    @JoinColumn(name="classgroup",referencedColumnName = "groupName")
    private Map<String, Student> students;

    public ClassGroup() {
    }

    public ClassGroup(String id, GroupType groupType,Map<String, Student> students) {
        this.groupName=id;
        this.students = students;
        this.groupType=groupType;
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
