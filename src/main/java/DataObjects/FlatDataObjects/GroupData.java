package DataObjects.FlatDataObjects;

import DataObjects.APIObjects.StudentData;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupData {
    private String name;
    private GroupType groupType;
    private List<StudentData> students;
    private int points;

    public GroupData(String groupName, GroupType groupType, int points, Stream<StudentData> studentDataStream) {
        this.name = groupName;
        this.groupType = groupType;
        students=studentDataStream.collect(Collectors.toList());
        this.points=points;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupData groupData = (GroupData) o;
        return  Objects.equals(groupData.groupType, groupType) &&
                Objects.equals(groupData.name,name) &&
                Objects.equals(groupData.students,students);
    }

    public int getPoints() {
        return points;
    }
}
