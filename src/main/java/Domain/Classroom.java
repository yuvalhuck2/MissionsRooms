package Domain;

import javax.persistence.*;
import java.util.List;


@Entity
public class Classroom extends Participant{

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="classroom",referencedColumnName = "alias")
    private List<ClassGroup> classGroupA;

    //example one to one
//    @OneToOne(mappedBy = "classroom")
//    private ClassGroup classGroupB;

    public Classroom() {

    }

    public Classroom(String Id, ClassGroup classGroupA, ClassGroup classGroupB) {
        super(Id);
        //this.classGroupA = classGroupA;
        //this.classGroupB = classGroupB;
    }
}
