package DataAPI;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassRoomData classRoomData = (ClassRoomData) o;
        return  Objects.equals(classRoomData.groups, groups) &&
                Objects.equals(classRoomData.name,name);
    }
}
