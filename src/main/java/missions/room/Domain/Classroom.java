package missions.room.Domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Classroom{

    @Id
    private String className;

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="classroom",referencedColumnName = "className")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ClassGroup> classGroups;

    public Classroom() {

    }

    public Classroom(String id, ClassGroup classGroupA, ClassGroup classGroupB) {
        this.className=id;
        this.classGroups=new ArrayList<>();
        this.classGroups.add(classGroupA);
        this.classGroups.add(classGroupB);
    }

    public Student getStudent(String alias,GroupType groupType) {
        Student student=null;
        for(ClassGroup classGroup: classGroups){
            student=classGroup.getStudent(alias,groupType);
            if(student!=null){
                break;
            }
        }
        return student;
    }

    public ClassGroup getGroup(String participantKey) {
        for(ClassGroup classGroup: classGroups){
            if(classGroup.getGroupName().equals(participantKey)){
                return classGroup;
            }
        }
        return null;
    }

    public String getClassName() {
        return className;
    }
}
