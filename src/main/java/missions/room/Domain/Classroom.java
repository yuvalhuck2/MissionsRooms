package missions.room.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Classroom{

    @Id
    private String className;

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="classroom",referencedColumnName = "className")
    private List<ClassGroup> classGroups;

    public Classroom() {

    }

    public Classroom(String id, ClassGroup classGroupA, ClassGroup classGroupB) {
        this.className=id;
        this.classGroups=new ArrayList<>();
        this.classGroups.add(classGroupA);
        this.classGroups.add(classGroupB);
    }
}
