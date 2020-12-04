package DataAPI;

/**
 * user type - enum that tell if it is student, teacher, supervisor or IT
 */
public class RegisterDetailsData {
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private UserType userType;

    public RegisterDetailsData(String firstName, String lastName, String mail, String password, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}
