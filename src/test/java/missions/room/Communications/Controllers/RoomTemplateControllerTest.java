package missions.room.Communications.Controllers;

import Data.Data;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.ADD_TEMPLATE;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

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