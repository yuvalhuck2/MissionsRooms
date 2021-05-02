package DataObjects.APIObjects;

import DataObjects.FlatDataObjects.GroupType;

public class RegisterCodeDetailsData {

    private String alias;
    private String code;
    private String teacherAlias;
    private GroupType groupType;

    public RegisterCodeDetailsData(String alias, String code, String teacherAlias, GroupType groupType) {
        this.alias = alias;
        this.code = code;
        this.teacherAlias = teacherAlias;
        this.groupType = groupType;
    }

    public String getAlias() {
        return alias;
    }

    public String getCode() {
        return code;
    }

    public String getTeacherAlias() {
        return teacherAlias;
    }

    public GroupType getGroupType() {
        return groupType;
    }
}
