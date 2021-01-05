package DataAPI;

import java.util.ArrayList;
import java.util.List;

public class ClassRoomData {
    private String classroomName;
    private List<GroupData> groups;

    public ClassRoomData(String classroomName,List<GroupData> groupData) {
        this.classroomName = classroomName;
        this.groups = groupData;
    }

}
