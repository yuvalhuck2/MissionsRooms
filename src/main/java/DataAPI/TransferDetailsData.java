package DataAPI;

import DataObjects.FlatDataObjects.GroupType;

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

    public String getAlias() {
        return alias;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public GroupType getGroupType() {
        return groupType;
    }

}
