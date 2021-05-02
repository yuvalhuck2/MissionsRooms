package missions.room.Service;

import DataObjects.APIObjects.NewRoomDetails;
import DataObjects.APIObjects.RoomsDataByRoomType;
import DataObjects.FlatDataObjects.ClassRoomData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.FlatDataObjects.Response;
import missions.room.Managers.ProfileMessagesManager;
import missions.room.Managers.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomManager roomManager;

    @Autowired
    private ProfileMessagesManager messagesManager;

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


    /**
     * req 4.10 - approve or deny student's solution
     * @param roomId - the room identifier
     * @param missionId - the mission identifier
     * @param isApproved - if the solution was approved
     * @return if the mission was approved successfully
     */
    public Response<Boolean> responseStudentSolution(String apiKey,String roomId, String missionId, boolean isApproved){
        Response<String> response = roomManager.responseStudentSolution(apiKey, roomId, missionId, isApproved);
        if (response.getValue() == null) {
            return new Response<>(false, response.getReason());
        }
        Response<Set<String>> aliasesRes = roomManager.getAllRoomParticipants(roomId);
        if (aliasesRes.getReason() != OpCode.Success) {
            return new Response<>(false, aliasesRes.getReason());
        }
        String message = response.getValue();
        messagesManager.sendMultipleMessages(aliasesRes.getValue(), apiKey, message);
        return new Response<>(true, OpCode.Success);
    }



    /**
     * watch rooms details
     * @param apiKey
     * @return rooms details by type
     */
    public Response<List<RoomsDataByRoomType>> watchMyClassroomRooms (String apiKey){
        return roomManager.watchMyClassroomRooms(apiKey);
    }

    /**
     * disconnect from room
     * @param apiKey - authentication object
     * @return the mission details of the given room
     */
    public void disconnectFromRoom (String apiKey,String roomId){
        roomManager.disconnectFromRoom(apiKey,roomId);
    }

    /**
     * disconnect from all of my rooms
     * @param apiKey - authentication object
     */
    public void disconnectFromRooms(String apiKey) {

        roomManager.disconnectFromAllRooms(apiKey);
    }



}
