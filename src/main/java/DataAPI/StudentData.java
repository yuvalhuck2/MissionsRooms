package DataAPI;

public class StudentData {
    private String alias;
    private String firstName;
    private String lastName;


    public StudentData(String alias, String firstName, String lastName) {
        this.alias = alias;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
