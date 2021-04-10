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

    public Student(String alias, String firstName, String lastName, int points) {
        super(alias, firstName, lastName);
        this.points = points;
    }

    public Student(StudentData profileDetails) {
        super(profileDetails.getAlias(), profileDetails.getFirstName(), profileDetails.getLastName());
        points = 0;
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
        return new StudentData(alias,firstName,lastName, points);
    }

    @Override
    public UserProfileData getProfileData() {
        return new UserProfileData(firstName,lastName,alias,OpCode.Student,points);
    }

    public void deducePoints(int pointsToDeduce) {
        this.points-=pointsToDeduce;
    }
}
