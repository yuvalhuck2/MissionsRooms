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

    @Autowired
    private ManagerRoomStudent managerRoomStudent;


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
     * req 3.6.1 - watch details of the room
     * @param auth - authentication object
     * @return the mission details of the given room
     * TODO check on database if can generate unique string
     */
    public Response<List<RoomDetailsData>> watchRoomDetails (Auth auth){
        return managerRoomStudent.watchRoomDetails(auth.getApiKey());
    }

    /**
     * req 3.6.2.3 - answer deterministic question mission
     * @param auth - authentication object
     * @param roomId - room id
     * @param answer - answer for the question
     * @return if the answer was correct
     */
    public Response<Boolean> answerDeterministicQuestion(Auth auth,String roomId,Boolean answer){
        return managerRoomStudent.answerDeterministicQuestion(auth.getApiKey(),roomId,answer);
    }

    /**
     *
     * @param auth - authentication object
     * @return Classroom details
     */
    public Response<ClassRoomData> getClassRoomData(Auth auth){
        return roomManager.getClassRoomData(auth.getApiKey());
    }

}
