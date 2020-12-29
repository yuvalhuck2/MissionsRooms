package missions.room.Repo;

import CrudRepositories.ClassRoomRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomRepo {

    @Autowired
    private final ClassRoomRepository classRoomRepository;

    public ClassroomRepo(ClassRoomRepository classRoomRepository) {
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
}
