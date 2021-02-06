package DataAPI;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomData {
    private String name;
    private List<GroupData> groups;

    public ClassRoomData(String classroomName,List<GroupData> groupData) {
        this.name = classroomName;
        this.groups = groupData;
    }

    public String getName() {
        return name;
    }

    public List<GroupData> getGroups() {
        return groups;
    }
}
