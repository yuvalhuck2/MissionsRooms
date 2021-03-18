package DataAPI;

import java.util.Objects;

public class StudentData {
    private int points;
    private String alias;
    private String firstName;
    private String lastName;


    public StudentData(String alias, String firstName, String lastName, int points) {
        this.alias = alias;
        this.firstName = firstName;
        this.lastName = lastName;
        this.points=points;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentData studentData = (StudentData) o;
        return  Objects.equals(studentData.alias, alias) &&
                Objects.equals(studentData.firstName,firstName) &&
                Objects.equals(studentData.lastName,lastName);
    }
}
