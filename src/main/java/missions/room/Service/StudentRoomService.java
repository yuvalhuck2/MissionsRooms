package missions.room.Service;

import DataAPI.Response;
import DataAPI.RoomDetailsData;
import DataAPI.SolutionData;
import missions.room.Managers.ManagerRoomStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * TODO refactor the name to watchMyRooms from the controller to the manager
     */
    public Response<List<RoomDetailsData>> watchRoomDetails (String apiKey){
        return managerRoomStudent.watchRoomDetails(apiKey);
    }

    /**
     * req 3.6.1 - watch data of a specific room
     * @param apiKey - authentication object
     * @return the mission details of the given room
     */
    public Response<RoomDetailsData> watchRoomData (String apiKey,String roomId){
        return managerRoomStudent.watchRoomData(apiKey,roomId);
    }

    /**
     * disconnect from room
     * @param apiKey - authentication object
     * @return the mission details of the given room
     */
    public void disconnectFromRoom (String apiKey,String roomId){
        managerRoomStudent.disconnectFromRoom(apiKey,roomId);
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

    /**
     * req3.6.2.4 - answer story mission
     * @param apiKey - authentication object
     * @param roomId - room id
     * @param sentence - the next sentence to add to the story
     * @return - if the story was added successfully
     */
    public Response<Boolean> answerStoryMission(String apiKey,String roomId, String sentence){
        return managerRoomStudent.answerStoryMission(apiKey, roomId, sentence);
    }

    public Response<Boolean> answerTriviaMission(String apiKey, String roomId, Boolean answer){
        return managerRoomStudent.answerTriviaMission(apiKey, roomId, answer);
    }

    /**
     * disconnect from all of my rooms
     * @param apiKey - authentication object
     */
    public void disconnectFromRooms(String apiKey) {
        managerRoomStudent.disconnectFromAllRooms(apiKey);
    }

    /**
     * req 3.6.2.1 - answer open question mission
     * @param apiKey
     * @param openAnswer
     * @param file
     * @return if the answer was accepted successfully
     */
    public Response<Boolean> answerOpenQuestionMission(String apiKey, SolutionData openAnswer, MultipartFile file){
        return managerRoomStudent.answerOpenQuestionMission(apiKey, openAnswer, file);
    }

    /**
     * req3.6.2.4 - answer story mission
     * @param apiKey - authentication object
     * @param roomId - room id
     */
    public Response<Boolean> finishStoryMission(String apiKey,String roomId){
        return managerRoomStudent.finishStoryMission(apiKey, roomId);
    }
}
