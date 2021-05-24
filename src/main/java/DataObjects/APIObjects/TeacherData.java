package DataObjects.APIObjects;

import DataObjects.FlatDataObjects.GroupType;

public class TeacherData {
    private String firstName;
    private String lastName;
    private String alias;
    private GroupType groupType;
    private String apiKey;
    private boolean isSupervisor;

    public TeacherData(String firstName, String lastName, String alias,GroupType groupType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.groupType=groupType;
        isSupervisor = false;
    }

    public TeacherData(StudentData profileDetails) {
        this.firstName = profileDetails.getFirstName();
        this.lastName = profileDetails.getLastName();
        this.alias = profileDetails.getAlias();
        isSupervisor = false;
    }

    public TeacherData(String alias, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        isSupervisor = false;
    }

    public TeacherData(String alias, String firstName, String lastName, boolean isSupervisor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.isSupervisor = isSupervisor;
    }

    public boolean isSupervisor() {
        return isSupervisor;
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

    public String getApiKey() {
        return apiKey;
    }
}
