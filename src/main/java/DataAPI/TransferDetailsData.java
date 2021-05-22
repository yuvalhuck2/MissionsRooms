package DataAPI;

public class TransferDetailsData {
    private String apiKey;
    private String alias;
    private String classroomName;
    private GroupType groupType;

    public TransferDetailsData(String apiKey, String alias, String classroomName,GroupType groupType) {
        this.apiKey = apiKey;
        this.alias = alias;
        this.classroomName = classroomName;
        this.groupType=groupType;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }
}
