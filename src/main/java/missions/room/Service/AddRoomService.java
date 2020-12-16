package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.newRoomDetails;
import missions.room.Managers.AddRoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddRoomService {

    @Autowired
    private AddRoomManager addRoomManager;

    public Response<Boolean> createRoom(Auth auth, newRoomDetails roomDetails){
        return addRoomManager.createRoom(auth.getApiKey(),roomDetails);
    }
}
