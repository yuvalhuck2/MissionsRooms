package missions.room.Managers;

import CrudRepositories.TeacherCrudRepository;
import DataAPI.*;
import missions.room.Domain.*;
import missions.room.Domain.Users.Teacher;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * class for all the managers that need to find a teacher for their methods
 */
@Service
public abstract class TeacherManager {

    @Autowired
    protected TeacherRepo teacherRepo;

    protected Ram ram;

    public TeacherManager() {
        ram=new Ram();
    }

    public TeacherManager(Ram ram, TeacherCrudRepository teacherCrudRepository) {
        this.teacherRepo = new TeacherRepo(teacherCrudRepository);
        this.ram=ram;
    }

    public TeacherManager(TeacherCrudRepository teacherCrudRepository) {
        this.teacherRepo = new TeacherRepo(teacherCrudRepository);
        this.ram=new Ram();
    }

    protected Response<Teacher> checkTeacher(String apiKey){
        String alias=ram.getAlias(apiKey);
        if(alias==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<Teacher> teacherResponse=teacherRepo.findTeacherById(alias);
        return checkTeacherByAlias(teacherResponse);
    }

    @Transactional
    protected Response<Teacher> checkTeacherForRead(String alias){
        Response<Teacher> teacherResponse=teacherRepo.findTeacherForRead(alias);
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }
        Teacher teacher=teacherResponse.getValue();
        if(teacher==null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(teacher,OpCode.Success);
    }


    protected Response<Teacher> checkTeacherByAlias(Response<Teacher> teacherResponse){
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(null,teacherResponse.getReason());
        }
        Teacher teacher=teacherResponse.getValue();
        if(teacher==null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(teacher,OpCode.Success);
    }


}
