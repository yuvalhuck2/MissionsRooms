package missions.room.Communications.Controllers;

import Data.Data;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static Data.APIPaths.ADD_TEMPLATE;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

@RunWith(SpringRunner.class)
class RoomTemplateControllerTest extends ControllerTest {

    @Test
    void createRoomTemplate() {
        String body = gson.toJson(dataGenerator.getRoomTemplateData(Data.VALID));
        testControllerWithBody(ADD_TEMPLATE, body, Wrong_Key, false);
    }

    @Test
    void searchTemplate() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(ADD_TEMPLATE, body, Wrong_Key, false);
    }
}