package missions.room.Domain.Users;

import DataObjects.APIObjects.TeacherData;
import DataObjects.FlatDataObjects.GroupType;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.UserProfileData;
import missions.room.Domain.ClassGroup;
import missions.room.Domain.Classroom;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.transaction.Transactional;


@Entity
@Configurable
public class Teacher extends SchoolUser {

    @ManyToOne
    private Classroom classroom;

    @Enumerated(EnumType.ORDINAL)
    protected GroupType groupType;

    public Teacher() {
        super();
    }

    public Teacher(TeacherData profileDetails) {
        super(profileDetails.getAlias(), profileDetails.getFirstName(), profileDetails.getLastName());
    }

    @Override
    public OpCode getOpcode() {
        return OpCode.Teacher;
    }

    public Teacher(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType) {
        super(alias, firstName, lastName);
        this.classroom = classroom;
        this.groupType = groupType;
    }

    public Teacher(String alias, String firstName, String lastName, Classroom classroom, GroupType groupType,String password) {
        super(alias, firstName, lastName);
        this.classroom = classroom;
        this.groupType = groupType;
        this.password=password;
    }

    public Teacher(String alias, String firstName, String lastName,String password) {
        super(alias, firstName, lastName);
        this.password=password;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public Student getStudent(String alias) {
        if(classroom!=null){
            return classroom.getStudent(alias,groupType);
        }
        return null;
    }

    public Student getUnregisteredStudent(String alias){
        if(classroom!=null){
            return classroom.getStudent(alias, GroupType.C);
        }
        return null;
    }

    public ClassGroup getGroup(String participantKey) {
        if(classroom!=null) {
            return classroom.getGroup(participantKey);
        }
        return null;
    }

    public ClassGroup getGroupByName(String participantKey) {
        if(classroom!=null) {
            return classroom.getGroupByName(participantKey);
        }
        return null;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public boolean isSupervisor() {
        return false;
    }

    public TeacherData getData() {
        return new TeacherData(firstName,lastName,alias,groupType);
    }

    @Transactional
    public Response<Boolean> moveStudentToMyGroup(String student, GroupType groupType) {
        if(this.groupType==GroupType.BOTH||this.groupType==groupType){
           return classroom.moveStudentToMyGroup(student,groupType);
        }
        return new Response<>(false,OpCode.Not_Exist_Group);
    }

    @Override
    public UserProfileData getProfileData() {
        return new UserProfileData(firstName,lastName,alias,getOpcode(),classroom!=null);
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setGroupType(GroupType groupType) {
        this.groupType = groupType;
    }
}