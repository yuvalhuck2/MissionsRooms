package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.RoomDetailsData;
import missions.room.Managers.ManagerRoomStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentRoomService {

    @Autowired
    private ManagerRoomStudent managerRoomStudent;

    /**
     * req 3.6.1 - watch details of the room
     * @param apiKey - authentication object
     * @return the mission details of the given room
     * TODO check on database if can generate unique string
     */
    public Response<List<RoomDetailsData>> watchRoomDetails (String apiKey){
        return managerRoomStudent.watchRoomDetails(apiKey);
    }

    /**
     * req 3.6.2.3 - answer deterministic question mission
     * @param apiKey - authentication object
     * @param roomId - room id
     * @param answer - answer for the question
     * @return if the answer was correct
     */
    public Response<Boolean> answerDeterministicQuestion(String apiKey,String roomId,Boolean answer){
        return managerRoomStudent.answerDeterministicQuestion(apiKey,roomId,answer);
    }

}
