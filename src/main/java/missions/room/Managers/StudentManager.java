package missions.room.Managers;


import CrudRepositories.StudentCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Ram;
import missions.room.Domain.Student;
import missions.room.Repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StudentManager {

    @Autowired
    protected StudentRepo studentRepo;

    protected Ram ram;

    public StudentManager() {
        ram=new Ram();
    }

    public StudentManager(Ram ram, StudentCrudRepository studentCrudRepository) {
        this.studentRepo = new StudentRepo(studentCrudRepository);
        this.ram=ram;
    }

    public StudentManager(StudentCrudRepository studentCrudRepository) {
        this.studentRepo = new StudentRepo(studentCrudRepository);
        this.ram=new Ram();
    }

    protected Response<Student> checkStudent(String apiKey){
        String alias=ram.getAlias(apiKey);
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
