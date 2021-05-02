package missions.room.Repo;

import CrudRepositories.RoomCrudRepository;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.ClassroomRoom;
import missions.room.Domain.Rooms.GroupRoom;
import missions.room.Domain.Rooms.StudentRoom;
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

    @Transactional
    public Response<StudentRoom> findStudentRoomForWriteByAlias(String alias) {
        try{
            return new Response<>(roomCrudRepository.findStudentRoomForWriteByAlias(alias), OpCode.Success);
        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<GroupRoom> findGroupRoomForWriteByAlias(String groupName) {
        try{
            return new Response<>(roomCrudRepository.findGroupRoomForWriteByAlias(groupName), OpCode.Success);
        }
//        catch(InvalidDataAccessApiUsageException e){
//            return new Response<>(null, OpCode.Success);
//        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    @Transactional
    public Response<ClassroomRoom> findClassroomRoomForWriteByAlias(String classroomName) {
        try{
            return new Response<>(roomCrudRepository.findClassroomRoomForWriteByAlias(classroomName), OpCode.Success);
        }
//        catch(InvalidDataAccessApiUsageException e){
//            return new Response<>(null, OpCode.Success);
//        }
        catch(LockTimeoutException e){
            return new Response<>(null,OpCode.TimeOut);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }

    public Response<Boolean> deleteRoom(Room room){
        try {
            roomCrudRepository.delete(room);
            return new Response<>(true,OpCode.Success);
        }
        catch (Exception e){
            return new Response<>(false,OpCode.DB_Error);
        }
    }



    public Response<StudentRoom> findStudentRoomByAlias(String alias) {
        try{
            return new Response<>(roomCrudRepository.findStudentRoomByAlias(alias), OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }


    public Response<GroupRoom> findGroupRoomByAlias(String groupName) {
        try{
            return new Response<>(roomCrudRepository.findGroupRoomByAlias(groupName), OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }


    public Response<ClassroomRoom> findClassroomRoomByAlias(String classroomName) {
        try{
            return new Response<>(roomCrudRepository.findClassroomRoomByAlias(classroomName), OpCode.Success);
        }
        catch(Exception e){
            return new Response<>(null,OpCode.DB_Error);
        }
    }



}
