package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.RoomDetailsData;
import DataAPI.newRoomDetails;
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

    @Autowired
    private ManagerRoomStudent managerRoomStudent;

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

    /**
     * req 3.6.1 - watch details of the room
     * @param auth - authentication object
     * @return the mission details of the given room
     * TODO check on database if can generate unique string
     */
    public Response<List<RoomDetailsData>> watchRoomDetails (Auth auth){
        return managerRoomStudent.watchRoomDetails(auth.getApiKey());
    }
}
