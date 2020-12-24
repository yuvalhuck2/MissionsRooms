package missions.room.Domain;

import DataAPI.OpCode;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Student extends SchoolUser {

    public Student() {
    }

    @Override
    public OpCode getOpcode() {
        return OpCode.Student;
    }

    public Student(String alias, String firstName, String lastName) {
        super(alias, firstName, lastName);
    }
}
