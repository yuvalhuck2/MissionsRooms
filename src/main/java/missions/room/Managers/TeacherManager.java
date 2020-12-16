package missions.room.Managers;

import CrudRepositories.TeacherCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Ram;
import missions.room.Domain.Teacher;
import missions.room.Repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class for all the managers that need to find a teacher for their methods
 */
@Service
public abstract class TeacherManager {

    @Autowired
    protected TeacherRepo teacherRepo;

    protected final Ram ram;

    public TeacherManager() {
        ram=new Ram();
    }

    public TeacherManager(Ram ram, TeacherCrudRepository teacherCrudRepository) {
        this.teacherRepo = new TeacherRepo(teacherCrudRepository);
        this.ram=ram;
    }

    protected Response<Boolean> checkTeacher(String apiKey){
        String alias=ram.getApi(apiKey);
        if(alias==null){
            return new Response<>(false, OpCode.Wrong_Key);
        }
        Response<Teacher> teacherResponse=teacherRepo.findTeacherById(alias);
        if(teacherResponse.getReason()!= OpCode.Success){
            return new Response<>(false,teacherResponse.getReason());
        }
        Teacher teacher=teacherResponse.getValue();
        if(teacher==null){//teacher repo not mock
            return new Response<>(false,OpCode.Not_Exist);
        }
        return new Response<>(true,OpCode.Success);
    }
}
