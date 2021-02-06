package missions.room.Repo;

import CrudRepositories.ClassroomRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import missions.room.Domain.Teacher;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomRepo {

    @Autowired
    private final ClassroomRepository classRoomRepository;

    public ClassroomRepo(ClassroomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }

    public Response<Boolean> saveAll(List<Classroom> classes) {
        try{
            classRoomRepository.saveAll(classes);
            return new Response<Boolean>(true, OpCode.Success);
        } catch (Exception e) {
            return new Response<>(false,OpCode.DB_Error);
        }
    }

    public Response<Classroom> save(Classroom classRoom){
        try {
            return new Response<>(classRoomRepository.save(classRoom), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Classroom> findClassroomByStudent(String student) {

        try {
            Classroom classroom=classRoomRepository.findClassroomByStudent(student);
            if(classroom==null){
                classroom= null;
            }
            return new Response<>(classroom, OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
