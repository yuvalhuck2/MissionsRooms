package missions.room.AcceptanceTestDataObjects;

public class RegisterDetailsTest {

    private String fName;
    private String lName;
    private String hashedPassword;
    private String alias;
    private UserTypeTest type;
    private String classRoom;

    public RegisterDetailsTest(String fName, String lName, String hashedPassword, String alias, UserTypeTest type, String classRoom) {
        this.fName = fName;
        this.lName = lName;
        this.hashedPassword = hashedPassword;
        this.alias = alias;
        this.type = type;
        this.classRoom = classRoom;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getAlias() {
        return alias;
    }

    public UserTypeTest getType() {
        return type;
    }

    public String getClassRoom() {
        return classRoom;
    }
}
