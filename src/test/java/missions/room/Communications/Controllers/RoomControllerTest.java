package missions.room.Communications.Controllers;

import Data.Data;
import DataObjects.APIObjects.ResolveMissionData;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

class RoomControllerTest extends ControllerTest {

    @Test
    void createRoom() {
        String body = gson.toJson(dataGenerator.getNewRoomDetails(Data.Valid_Student));
        testControllerWithBody(ADD_ROOM, body, Wrong_Key, false);
    }

    @Test
    void closeRoom() {
        String body = gson.toJson(roomIdAndApiKey);
        testControllerWithBody(CLOSE_ROOM, body, Wrong_Key, false);
    }

    @Test
    void getClassRoomData() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(GET_CLASSROOM, body, Wrong_Key, null);
    }

    @Test
    void responseStudentSolution() {
        ResolveMissionData resolveMissionData = new ResolveMissionData("apiKEy","roomId", "missionId", true);
        String body = gson.toJson(resolveMissionData);
        testControllerWithBody(RESPONSE_ANSWER, body, Wrong_Key, false);
    }

    @Test
    void watchRoomDetails() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(WATCH_TEACHER_ROOMS, body, Wrong_Key, null);
    }
}