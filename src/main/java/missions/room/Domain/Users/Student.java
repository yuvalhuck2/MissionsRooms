package missions.room.Domain.Users;

import DataAPI.OpCode;
import DataAPI.StudentData;
import DataAPI.UserProfileData;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Student extends SchoolUser {

    private int points;

    public Student() {
        super();
    }

    @Override
    public OpCode getOpcode() {
        return OpCode.Student;
    }

    public Student(String alias, String firstName, String lastName) {

        super(alias, firstName, lastName);
        this.points=0;
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public int getPoints() {
        return points;
    }

    public StudentData getStudentData() {
        return new StudentData(alias,firstName,lastName);
    }


    public UserProfileData getProfileData() {
        return new UserProfileData(firstName,lastName,alias,OpCode.Student,points);
    }
}
