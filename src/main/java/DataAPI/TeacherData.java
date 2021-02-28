package DataAPI;

public class TeacherData {
    private String firstName;
    private String lastName;
    private String alias;
    private GroupType groupType;

    public TeacherData(String firstName, String lastName, String alias,GroupType groupType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.groupType=groupType;
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

    public GroupType getGroupType() {
        return groupType;
    }
}
