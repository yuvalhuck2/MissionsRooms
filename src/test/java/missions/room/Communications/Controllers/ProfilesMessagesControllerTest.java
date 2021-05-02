package missions.room.Communications.Controllers;

import Data.Data;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.DB_Error;
import static DataObjects.FlatDataObjects.OpCode.Not_Exist;

class ProfilesMessagesControllerTest extends ControllerTest {

    @Test
    void sendMessage() {
        String body = gson.toJson(dataGenerator.getMessageData(Data.VALID));
        testControllerWithBody(WATCH_POINTS_TABLE, body, Not_Exist, null);
    }

    @Test
    void viewMessages() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(WATCH_MESSAGES, body, DB_Error, null);
    }

    @Test
    void deleteMessage() {
        String body = gson.toJson(dataGenerator.getMessageData(Data.VALID));
        testControllerWithBody(DELETE_MESSAGE, body, Not_Exist, false);
    }

    @Test
    void watchProfile() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(WATCH_PROFILE, body, Not_Exist, null);
    }
}