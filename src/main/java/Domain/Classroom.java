package Domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;


@Entity
public class Classroom extends Participant{

    @OneToOne
    private Group groupA;

    @OneToOne
    private Group groupB;

    public Classroom() {
    }

    public Classroom(String Id,Group groupA, Group groupB) {
        super(Id);
        this.groupA = groupA;
        this.groupB = groupB;
    }
}
