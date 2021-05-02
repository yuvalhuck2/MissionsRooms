package missions.room.Communications.Controllers;

import Data.Data;
import DataObjects.APIObjects.CreateMissionData;
import missions.room.Domain.missions.Mission;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

@RunWith(SpringRunner.class)
class MissionControllerTest extends ControllerTest {

    @Test
    void testCreatMission() {
        Mission mission = dataGenerator.getMission(Data.Valid_Deterministic);
        String body = gson.toJson(new CreateMissionData(mission.getMissionId(), gson.toJson(mission)));
        testControllerWithBody(ADD_MISSION, body, Wrong_Key, false);
    }

    @Test
    void searchMissions() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(SEARCH_MISSION, body, Wrong_Key, null);
    }

    @Test
    void getUnApproveAnswers() {
        String body = gson.toJson(roomIdAndApiKey);
        testControllerWithBody(APPROVE_ANSWER, body, Wrong_Key, null);
    }

    //TODO
    @Test
    void downloadOpenAnswerFile() {
    }
}