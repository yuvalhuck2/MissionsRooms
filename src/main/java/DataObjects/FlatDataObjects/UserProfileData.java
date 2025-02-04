package DataObjects.FlatDataObjects;

import java.util.Objects;

public class UserProfileData {
    private String apiKey;
    private final String firstName;
    private final String lastName;
    private final String alias;
    private final OpCode userType;
    private final Integer points;
    private final boolean hasClassroom;


    public UserProfileData(String firstName, String lastName, String alias, OpCode userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.userType = userType;
        this.points = null;
        this.hasClassroom=false;
    }

    public UserProfileData(String firstName, String lastName, String alias, OpCode userType, int points) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.userType = userType;
        this.points = points;
        this.hasClassroom=false;
    }

    public UserProfileData(String alias, OpCode userType) {
        this.alias=alias;
        firstName=null;
        lastName=null;
        this.userType=userType;
        this.points = null;
        this.hasClassroom=false;
    }

    public UserProfileData(String alias, String lastName, String firstName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        userType = OpCode.Student;
        points = 0;
        this.hasClassroom=false;
    }

    public UserProfileData(String firstName, String lastName, String alias, OpCode userType,boolean hasClassroom) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.userType = userType;
        this.points = null;
        this.hasClassroom=hasClassroom;
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

    public OpCode getUserType() {
        return userType;
    }


    public Integer getPoints() {
        return points;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean getHasClassroom() {
        return hasClassroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfileData that = (UserProfileData) o;
        return Objects.equals(points, that.points) &&
                Objects.equals(alias, that.alias) &&
                userType == that.userType &&
                userType == OpCode.IT ||
                (
                        Objects.equals(firstName, that.firstName) &&
                                Objects.equals(lastName, that.lastName)
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, firstName, lastName, alias, userType, points);
    }
}
