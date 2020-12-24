package missions.room.AcceptanceTests.AcceptanceTestDataObjects;

public class UserTestData {
    private String fName;
    private String lName;
    private String alias;

    public UserTestData(String fName, String lName, String alias) {
        this.fName = fName;
        this.lName = lName;
        this.alias = alias;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getAlias() {
        return alias;
    }
}
