package missions.room.Service;

import DataAPI.*;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomManager roomManager;

    /**
     * req 4.1 -create room
     * @param apiKey -  authentication object
     * @param roomDetails - the room details
     * @return if the room was added
     */
    public Response<Boolean> createRoom(String apiKey, NewRoomDetails roomDetails){
        return roomManager.createRoom(apiKey,roomDetails);
    }

    /**
     * req 4.2 - close missions room
     * @param apiKey - authentication object
     * @param roomId - the identifier of the room
     * @return if the room was closed successfully
     */
    public Response<Boolean> closeRoom(String apiKey, String roomId){

        return roomManager.closeRoom(apiKey,roomId);
    }

    /**
     *
     * @param apiKey - authentication object
     * @return Classroom details
     */
    public Response<ClassRoomData> getClassRoomData(String apiKey){
        return roomManager.getClassRoomData(apiKey);
    }

}
