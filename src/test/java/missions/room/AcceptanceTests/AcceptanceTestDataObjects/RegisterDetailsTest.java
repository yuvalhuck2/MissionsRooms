package missions.room.AcceptanceTests.AcceptanceTestDataObjects;

public class RegisterDetailsTest {

    private String hashedPassword;
    private String alias;

    public RegisterDetailsTest(String fName, String lName, String hashedPassword, String alias, UserTypeTest type, String classRoom) {
        this.hashedPassword = hashedPassword;
        this.alias = alias;
    }


    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getAlias() {
        return alias;
    }

}
