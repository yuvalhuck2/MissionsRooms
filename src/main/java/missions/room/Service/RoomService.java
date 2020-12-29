package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.newRoomDetails;
import missions.room.Managers.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class RoomService {

    @Autowired
    private RoomManager roomManager;

    public Response<Boolean> createRoom(Auth auth, newRoomDetails roomDetails){
        return roomManager.createRoom(auth.getApiKey(),roomDetails);
    }

    /**
     * req 4.2 - close missions room
     * @param auth - authentication object
     * @param roomId - the identifier of the room
     * @return if the room was closed successfully
     */
    public Response<Boolean> closeRoom(Auth auth, String roomId){

        return roomManager.closeRoom(auth.getApiKey(),roomId);
    }
}
