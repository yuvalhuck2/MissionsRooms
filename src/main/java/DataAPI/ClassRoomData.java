package DataAPI;

import java.util.List;
import java.util.Objects;

public class ClassRoomData {
    private String apiKey;
    private String name;
    private List<GroupData> groups;
    private int points;

    public ClassRoomData(String classroomName, int points, List<GroupData> groupData) {
        this.name = classroomName;
        this.groups = groupData;
        this.points =points;
    }

    public String getName() {
        return name;
    }

    public List<GroupData> getGroups() {
        return groups;
    }

    public int getPoints() {
        return points;
    }

    public String getApiKey() {
        return apiKey;
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
