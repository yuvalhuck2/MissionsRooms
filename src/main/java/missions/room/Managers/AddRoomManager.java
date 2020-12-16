package missions.room.Managers;

import CrudRepositories.RoomCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.newRoomDetails;
import Utils.Utils;
import missions.room.Domain.Ram;
import missions.room.Domain.Room;
import missions.room.Domain.RoomTemplate;
import missions.room.Repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddRoomManager extends TeacherManager {

    @Autowired
    private RoomRepo roomRepo;

    public AddRoomManager() {
        super();
    }

    public AddRoomManager(Ram ram, TeacherCrudRepository teacherCrudRepository, RoomCrudRepository roomCrudRepository) {
        super(ram, teacherCrudRepository);
        this.roomRepo = new RoomRepo(roomCrudRepository);
    }

    public Response<Boolean> createRoom(String apiKey, newRoomDetails roomDetails){
        Response<Boolean> checkTeacher=checkTeacher(apiKey);
        if(checkTeacher.getReason()!=OpCode.Success){
            return checkTeacher;
        }
        Response<Room> roomResponse= validateDetails(roomDetails);
        if(roomResponse.getReason()!=OpCode.Success){
            return new Response<>(false,roomResponse.getReason());
        }
        return saveRoom(roomResponse.getValue());
    }

    private Response<Boolean> saveRoom(Room room) {
        Response<Room> roomResponse=roomRepo.save(room);
        if(roomResponse.getReason()!= OpCode.Success){
            return new Response<>(false,roomResponse.getReason());
        }

        return new Response<>(true,OpCode.Success);
    }

    private Response<Room> validateDetails(newRoomDetails roomDetails) {
        if(!Utils.checkString(roomDetails.getRoomName())){
            return new Response<>(null,OpCode.Wrong_Name);
        }
        if(roomDetails.getRoomType()==null){
            return new Response<>(null,OpCode.Wrong_Type);
        }
        //by room type check if the type exist

    }
}
