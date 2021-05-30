package DataObjects.FlatDataObjects;

import java.util.List;

public class RecordTableData {

    private List<PointsData> studentsPointsData;

    private List<PointsData>  groupsPointsData;

    private List<PointsData>  classroomsPointsData;

    private boolean isSupervisor;

    public RecordTableData(List<PointsData> studentsPointsData,
                           List<PointsData> groupsPointsData,
                           List<PointsData> classroomsPointsData) {
        this.studentsPointsData =studentsPointsData;
        this.groupsPointsData =groupsPointsData;
        this.classroomsPointsData = classroomsPointsData;
    }

    public RecordTableData(List<PointsData> studentsPointsData,
                           List<PointsData> groupsPointsData,
                           List<PointsData> classroomsPointsData,boolean isSupervisor) {
        this.studentsPointsData =studentsPointsData;
        this.groupsPointsData =groupsPointsData;
        this.classroomsPointsData = classroomsPointsData;
        this.isSupervisor = isSupervisor;
    }

    public List<PointsData> getStudentsPointsData() {
        return studentsPointsData;
    }

    public List<PointsData> getGroupsPointsData() {
        return groupsPointsData;
    }

    public List<PointsData> getClassroomsPointsData() {
        return classroomsPointsData;
    }

    public boolean isSupervisor() {
        return isSupervisor;
    }
}
