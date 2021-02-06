package missions.room.Domain;

import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.TeacherData;
import missions.room.Repo.MissionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import missions.room.*;

import javax.persistence.*;
import javax.transaction.Transactional;


@Entity
@Configurable
public class Teacher extends SchoolUser {

    @ManyToOne
    private Classroom classroom;

    @Enumerated(EnumType.ORDINAL)
    protected GroupType groupType;

    public Teacher() {
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
}