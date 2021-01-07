package DataAPI;

import missions.room.Domain.GroupType;
import java.util.List;

public class GroupData {
    private String name;
    private GroupType groupType;
    private List<StudentData> students;

    public GroupData(String groupName, GroupType groupType) {
        this.name = groupName;
        this.groupType = groupType;
    }

    public void setGroupName(String groupName) {
        this.name = groupName;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public void setStudents(List<StudentData> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public List<StudentData> getStudents() {
        return students;
    }
}
