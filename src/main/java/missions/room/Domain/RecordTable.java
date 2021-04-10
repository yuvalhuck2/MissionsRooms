package missions.room.Domain;

import DataAPI.GroupType;
import DataAPI.PointsData;
import DataAPI.RecordTableData;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import java.util.stream.Collectors;

public class RecordTable {

    private static final PointsComparator pointsComparator = new PointsComparator();

    private LocalDateTime timeStamp;

    private ConcurrentHashMap<String, PointsData> studentsPointsData;

    private ConcurrentHashMap<String,PointsData>  groupsPointsData;

    private ConcurrentHashMap<String,PointsData>  classroomsPointsData;

    public RecordTable() {
        timeStamp = LocalDateTime.now();
        studentsPointsData = new ConcurrentHashMap<>();
        groupsPointsData = new ConcurrentHashMap<>();
        classroomsPointsData = new ConcurrentHashMap<>();
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void addClassroomData(PointsData pointsData){
        classroomsPointsData.put(pointsData.getName(),pointsData);
    }

    public void addGroupData(PointsData pointsData){
        groupsPointsData.put(pointsData.getName(),pointsData);
    }

    public void addStudentData(PointsData pointsData){
        studentsPointsData.put(pointsData.getName(),pointsData);
    }

    public void updateTimeStamp() {
        timeStamp=LocalDateTime.now();
    }

    public RecordTableData getData() {
        return new RecordTableData(sortPointsListFromMap(studentsPointsData),
                sortPointsListFromMap(groupsPointsData),
                sortPointsListFromMap(classroomsPointsData));
    }

    private List<PointsData> sortPointsListFromMap(ConcurrentHashMap<String, PointsData> pointsDataMap) {
        return pointsDataMap.values().
                stream().
                sorted(new PointsComparator()).
                collect(Collectors.toList());
    }

    public RecordTableData getData(Classroom classroom) {
        return new RecordTableData(getStudentsPointsList(classroom.getStudentsAlias()),
                sortPointsListFromMap(groupsPointsData),
                sortPointsListFromMap(classroomsPointsData));
    }

    private List<PointsData> getStudentsPointsList(List<String> studentsAlias) {
        ArrayList<PointsData> studentsPointsDataList = new ArrayList<>(studentsPointsData.values());
        for(String alias : studentsAlias){
            if(studentsPointsData.containsKey(alias)){
                PointsData pointsData = studentsPointsData.get(alias);
                studentsPointsDataList.remove(pointsData);
                studentsPointsDataList.add(new PointsData(pointsData.getName(),pointsData.getPoints(),true));
            }
        }
        studentsPointsDataList.sort(pointsComparator);
        return studentsPointsDataList;
    }

    public RecordTableData getSupervisorData() {
        return new RecordTableData(sortPointsListFromMap(studentsPointsData),
                sortPointsListFromMap(groupsPointsData),
                sortPointsListFromMap(classroomsPointsData),
                true);
    }
}

class PointsComparator implements Comparator<PointsData> {
    @Override
    public int compare(PointsData o1, PointsData o2) {
        return o2.getPoints() - o1.getPoints();
    }
}
