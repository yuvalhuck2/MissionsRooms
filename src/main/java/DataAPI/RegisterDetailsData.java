package DataAPI;

/**
 * user type - enum that tell if it is student, teacher, supervisor or IT
 */
public class RegisterDetailsData {
    private String firstName;
    private String lastName;
    private String alias;
    private String password;
    private UserType userType;
    private String classroom;

    public RegisterDetailsData(String firstName, String lastName, String alias, String password, UserType userType, String classroom) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
        this.userType = userType;
        this.classroom = classroom;
    }

    public RegisterDetailsData(String alias, String password, UserType userType) {
        this.alias = alias;
        this.password = password;
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAlias() {
        return alias;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
