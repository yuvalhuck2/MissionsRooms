package missions.room.Managers;

import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Student;
import missions.room.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class StudentTeacherManager extends TeacherManager {

    @Autowired
    protected StudentRepo studentRepo;

    protected Response<Student> checkStudent(String apiKey){
        String alias=ram.getApi(apiKey);
        if(alias==null){
            return new Response<>(null, OpCode.Wrong_Key);
        }
        Response<Student> studentResponse=studentRepo.findStudentById(alias);
        return checkStudentByAlias(studentResponse);
    }

    @Transactional
    protected Response<Student> checkStudentForRead(String alias){
        Response<Student> studentResponse=studentRepo.findStudentForRead(alias);
        if(studentResponse.getReason()!= OpCode.Success){
            return new Response<>(null,studentResponse.getReason());
        }
        Student student=studentResponse.getValue();
        if(student==null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(student,OpCode.Success);
    }


    protected Response<Student> checkStudentByAlias(Response<Student> studentResponse){
        if(studentResponse.getReason()!= OpCode.Success){
            return new Response<>(null,studentResponse.getReason());
        }
        Student student=studentResponse.getValue();
        if(student==null){
            return new Response<>(null,OpCode.Not_Exist);
        }
        return new Response<>(student,OpCode.Success);
    }
}