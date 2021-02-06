package missions.room.Repo;

import CrudRepositories.TeacherCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import missions.room.Domain.SchoolUser;
import missions.room.Domain.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherRepo {

    @Autowired
    private final TeacherCrudRepository teacherCrudRepository;

    public TeacherRepo(TeacherCrudRepository teacherCrudRepository) {
        this.teacherCrudRepository = teacherCrudRepository;
    }

    @Transactional
    public Response<Teacher> findTeacherForWrite(String alias){
        try {
            return new Response<Teacher>(teacherCrudRepository.findTeacherForWrite(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<Teacher> findTeacherForRead(String alias){
        try{
            return new Response<>(teacherCrudRepository.findTeacherForRead(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Teacher> findTeacherById(String alias){
        try{
            Optional<Teacher> teacherOptional=teacherCrudRepository.findById(alias);
            Teacher teacher=null;
            if(teacherOptional.isPresent()){
                teacher=teacherOptional.get();
            }
            return new Response<>(teacher, OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Boolean> saveAll(List<Teacher> teachers) {
        try{
            teacherCrudRepository.saveAll(teachers);
            return new Response<Boolean>(true, OpCode.Success);
        } catch (Exception e) {
            return new Response<>(false,OpCode.DB_Error);
        }
    }

    public Response<SchoolUser> save(Teacher teacher){
        try {
            return new Response<>(teacherCrudRepository.save(teacher), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }


    public Response<List<Teacher>> findTeacherByStudent(String student) {

        try {
            List<Teacher> teachers=teacherCrudRepository.findTeacherByStudent(student);
            if(teachers==null){
                teachers=new ArrayList<>();
            }
            return new Response<>(teachers, OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
