package missions.room.Domain;

import DataAPI.GroupData;
import DataAPI.GroupType;
import DataAPI.PointsData;
import DataAPI.RecordTableData;
import missions.room.Domain.Users.Student;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class ClassGroup {

    @Id
    private String groupName;

    @Enumerated(EnumType.ORDINAL)
    private GroupType groupType;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey
    @JoinColumn(name="classGroup",referencedColumnName = "groupName")
    private Map<String, Student> students;

    private int points;

    public ClassGroup() {
    }

    public ClassGroup(String id,GroupType groupType, Map<String, Student> students) {
        this.groupName=id;
        this.students = students;
        this.groupType = groupType;
        this.points=0;
    }

    public Student getStudent(String alias,GroupType groupType) {
        if(this.groupType==groupType||groupType==GroupType.BOTH)
            return students.get(alias);
        return null;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    @Transactional
    public void addStudent(Student student) {
        students.put(student.getAlias(),student);
    }

    @Transactional
    public Student removeStudent(String studentAlias) {
        Student student=students.get(studentAlias);
        students.remove(studentAlias);
        return student;
    }

    public Map<String,Student> getStudent(){
        return students;
    }

    public void addPoints(int points){
        this.points+=points;
    }


    public GroupData getGroupData(GroupType groupType) {
        if (checkGroup(groupType)){
            return new GroupData(this.groupName,
                    this.groupType,
                    this.points,
                    students.values().parallelStream().map(Student::getStudentData));
        }
        else{
            return null;
        }
    }

    /**
     * return if the group is owned by the teacher that ask for the group data
     */
    private boolean checkGroup(GroupType groupType) {
        return this.groupType!=GroupType.C&&(groupType==this.groupType||groupType==GroupType.BOTH)&&!students.isEmpty();
    }

    public Set<String> getStudentsAlias() {
        return students.keySet();
    }

    public boolean containsStudent(String alias) {
        return students.containsKey(alias);
    }

    public int getPoints() {
        return points;
    }

    public void getGroupPoints(RecordTable recordTableData, String className) {
        if(groupType != GroupType.C){
            recordTableData.addGroupData(new PointsData(className,groupType,points));
            for (Student student :
                    students.values()) {
                recordTableData.addStudentData(new PointsData(student.getAlias(),student.getPoints()));
            }
        }
    }
}
