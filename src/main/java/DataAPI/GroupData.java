package DataAPI;

import missions.room.Domain.GroupType;
import java.util.List;

public class GroupData {
    private String name;
    private GroupType groupType;
    private List<StudentData> students;

    public GroupData(String groupName, GroupType groupType) {
        this.groupName = groupName;
        this.groupType = groupType;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }

    public void setStudents(List<StudentData> students) {
        this.students = students;
    }
}
