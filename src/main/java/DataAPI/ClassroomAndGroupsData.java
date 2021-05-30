package DataAPI;

import DataObjects.FlatDataObjects.GroupType;

import java.util.List;

public class ClassroomAndGroupsData {
    private String classroom;
    private String name;
    private List<GroupType> groupTypes;

    public ClassroomAndGroupsData(String classroom,String name, List<GroupType> groupTypes) {
        this.classroom = classroom;
        this.groupTypes = groupTypes;
        this.name=name;
    }

    public String getClassroom() {
        return classroom;
    }

    public List<GroupType> getGroupTypes() {
        return groupTypes;
    }

    public String getName() {
        return name;
    }

}
