package Domain.Rooms;

import Domain.Student;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class StudentRoom extends Room {

    @OneToOne
    private Student participant;

    public StudentRoom() {
    }


}
