package DataAPI;

import missions.room.Domain.Classroom;

import java.util.Objects;

public class StudentData {
    private String apiKey;
    private int points;
    private String alias;
    private String firstName;
    private String lastName;
    private GroupType groupType;
    private String classroom;


    public StudentData(String alias, String firstName, String lastName, int points) {
        this.alias = alias;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points=points;
    }

    public StudentData(String alias, String firstName, String lastName, GroupType groupType, String className) {
        this.alias = alias;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points = 0;
        this.groupType = groupType;
        this.classroom = className;
    }

    public String getAlias() {
        return alias;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPoints() {
        return points;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public String getClassroom() {
        return classroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentData studentData = (StudentData) o;
        return  Objects.equals(studentData.alias, alias) &&
                Objects.equals(studentData.firstName,firstName) &&
                Objects.equals(studentData.lastName,lastName);
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom.getClassName();
    }

    public String getApKey() {
        return apiKey;
    }
}
