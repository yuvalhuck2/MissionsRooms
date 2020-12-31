package missions.room.Repo;

import CrudRepositories.ClassroomRoomCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Classroom;
import missions.room.Domain.Rooms.ClassroomRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomRoomRepo {
    @Autowired
    private ClassroomRoomCrudRepository classroomRoomCrudRepository;

    public Response<List<ClassroomRoom>> findClassroomRoomByClassroom(String classroom) {

        try {
            List<ClassroomRoom> classroomRooms=classroomRoomCrudRepository.findClassroomRoomByClassroom(classroom);
            if(classroomRooms==null){
                return new Response<>(null,OpCode.Not_Exist);
            }
            return new Response<>(classroomRooms, OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

}
