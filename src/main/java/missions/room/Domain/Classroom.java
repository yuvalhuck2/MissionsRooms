package missions.room.Domain;

import DataAPI.ClassRoomData;
import DataAPI.GroupData;
import DataAPI.OpCode;
import DataAPI.Response;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Entity
public class Classroom{

    @Id
    private String className;

    //@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="classroom",referencedColumnName = "className")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<ClassGroup> classGroups;

    private int points;

    public Classroom() {

    }

    public Classroom(String id, ClassGroup classGroupA, ClassGroup classGroupB) {
        this.className=id;
        this.classGroups=new HashSet<>();
        this.classGroups.add(classGroupA);
        this.classGroups.add(classGroupB);
        this.points=0;
    }

    public Classroom(String className, List<ClassGroup> classGroups) {
        this.className = className;
        this.classGroups = new HashSet<>(classGroups);
    }

    public Student getStudent(String alias, GroupType groupType) {
        Student student=null;
        for(ClassGroup classGroup: classGroups){
            student=classGroup.getStudent(alias,groupType);
            if(student!=null){
                break;
            }
        }
        return student;
    }

    public ClassGroup getGroup(String participantKey) {
        for(ClassGroup classGroup: classGroups){
            if(classGroup.getStudent().containsKey(participantKey)){
                return classGroup;
            }
        }
        return null;
    }

    public String getClassName() {
        return className;
    }

    public boolean addStudent(SchoolUser schoolUser, GroupType groupType) {
        for(ClassGroup classGroup: classGroups){
            if(classGroup.getGroupType().equals(groupType)){
                classGroup.addStudent((Student) schoolUser);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public Response<Boolean> moveStudentToMyGroup(String studentAlias, GroupType groupType) {
        ClassGroup c=null;
        ClassGroup toMoveto=null;
        for(ClassGroup classGroup: classGroups){
            if(classGroup.getGroupType()==GroupType.C){
                c=classGroup;
            }
            else if(classGroup.getGroupType()==groupType){
                toMoveto=classGroup;
            }
        }
        if(c==null||toMoveto==null){
            return new Response<>(false, OpCode.Not_Exist_Group);
        }
        Student student=c.removeStudent(studentAlias);
        if(student==null){//the student is already in a group
            return new Response<>(false, OpCode.Already_Exist_Student);
        }
        toMoveto.addStudent(student);
        return new Response<>(true,OpCode.Success);
    }

    public void addPoints(int points){
        this.points+=points;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public int getPoints() {
        return points;
    }

    public ClassGroup getGroupByName(String participantKey) {
        for(ClassGroup classGroup: classGroups){
            if(classGroup.getGroupName().equals(participantKey)){
                return classGroup;
            }
        }
        return null;
    }

    public ClassRoomData getClassroomData(GroupType groupType) {
        List<GroupData> groupDataList =classGroups.
                                        parallelStream().
                                        map((group)->group.getGroupData(groupType)).
                                        filter(Objects::nonNull).
                                        collect(Collectors.toList());

        return new ClassRoomData(className,groupDataList);
    }

    public List<String> getStudentsAlias() {
        ArrayList<String> aliases=new ArrayList<>();
        for (ClassGroup group: classGroups) {
            if(group.getGroupType()!=GroupType.C)
            aliases.addAll(group.getStudentsAlias());
        }
        return aliases;
    }
}
