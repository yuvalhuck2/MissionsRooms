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

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public List<GroupType> getGroupTypes() {
        return groupTypes;
    }

    public void setGroupTypes(List<GroupType> groupTypes) {
        this.groupTypes = groupTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
