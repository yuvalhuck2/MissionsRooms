package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.newRoomDetails;
import missions.room.Managers.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomManager roomManager;

    public Response<Boolean> createRoom(Auth auth, newRoomDetails roomDetails){
        return roomManager.createRoom(auth.getApiKey(),roomDetails);
    }
}
