package Domain.Rooms;

import Domain.ClassGroup;
import Domain.Student;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class GroupRoom extends Room {

    @OneToOne
    private ClassGroup participant;

    public GroupRoom() {
    }
}
