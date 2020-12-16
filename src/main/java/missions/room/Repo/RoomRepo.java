package missions.room.Repo;

import CrudRepositories.RoomCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockTimeoutException;
import java.util.Optional;

@Service
public class RoomRepo {

    @Autowired
    private RoomCrudRepository roomCrudRepository;

    public RoomRepo(RoomCrudRepository roomCrudRepository) {
        this.roomCrudRepository = roomCrudRepository;
    }

    @Transactional
    public Response<Room> findRoomForWrite(String id){
        try {
            return new Response<>(roomCrudRepository.findRoomForWrite(id), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<Room> findRoomForRead(String id){
        try{
            return new Response<>(roomCrudRepository.findRoomForRead(id), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Room> findRoomById(String id){
        try{
            Optional<Room> roomOptional= roomCrudRepository.findById(id);
            Room room=null;
            if(roomOptional.isPresent()){
                room=roomOptional.get();
            }
            return new Response<>(room, OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Room> save(Room room){
        try {
            return new Response<>(roomCrudRepository.save(room), OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }
}
