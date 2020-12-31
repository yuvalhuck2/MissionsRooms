package missions.room.Repo;

import CrudRepositories.StudentRoomCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentRoomRepo {

    @Autowired
    private StudentRoomCrudRepository studentRoomCrudRepository;

    public Response<List<StudentRoom>> findStudentRoomByStudent(String alias) {

        try {
            List<StudentRoom> studentRooms=studentRoomCrudRepository.findStudentRoomByStudent(alias);
            if(studentRooms==null){
                return new Response<>(null, OpCode.Not_Exist);
            }
            return new Response<>(studentRooms, OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

}
