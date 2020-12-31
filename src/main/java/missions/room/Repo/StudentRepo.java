package missions.room.Repo;

import CrudRepositories.StudentCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.SchoolUser;
import missions.room.Domain.Student;
import missions.room.Domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StudentRepo {
    @Autowired
    private final StudentCrudRepository studentCrudRepository;

    public StudentRepo(StudentCrudRepository studentCrudRepository) {
        this.studentCrudRepository = studentCrudRepository;
    }

    @Transactional
    public Response<Student> findStudentForWrite(String alias){
        try {
            return new Response<Student>(studentCrudRepository.findUserForWrite(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<Student> findStudentForRead(String alias){
        try{
            return new Response<>(studentCrudRepository.findUserForRead(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Student> findStudentById(String alias){
        try{
            Optional<Student> studentOptional=studentCrudRepository.findById(alias);
            Student student=null;
            if(studentOptional.isPresent()){
                student=studentOptional.get();
            }
            return new Response<>(student, OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Boolean> saveAll(List<Student> students) {
        try{
            studentCrudRepository.saveAll(students);
            return new Response<Boolean>(true, OpCode.Success);
        } catch (Exception e) {
            return new Response<>(false,OpCode.DB_Error);
        }
    }

    public Response<SchoolUser> save(Student student){
        try {
            return new Response<>(studentCrudRepository.save(student), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

}
