package DataAPI;

public class UserProfileData {
    private String firstName;
    private String lastName;
    private String mail;
    private UserType userType;
    private String classRoom;
    private String group;
    private String phone;
    //TODO talk about picture


    public UserProfileData(String firstName, String lastName, String mail, UserType userType, String classRoom, String group, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.userType = userType;
        this.classRoom = classRoom;
        this.group = group;
        this.phone=phone;
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

    public UserType getUserType() {
        return userType;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getGroup() {
        return group;
    }

    public String getPhone() {
        return phone;
    }
}
