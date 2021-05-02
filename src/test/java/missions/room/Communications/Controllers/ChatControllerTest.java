package missions.room.Communications.Controllers;

import Data.Data;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

class ChatControllerTest extends ControllerTest {

    @Test
    void testSendMessage() {
        String body = gson.toJson(dataGenerator.getChatMessageData(Data.VALID));
        testControllerWithBody(SEND_CHAT_MESSAGE, body, Wrong_Key, null);
    }

    @Test
    void testEnterChat() {
        String body = gson.toJson(dataGenerator.getNewRoomDetails(Data.Valid_Student));
        testControllerWithBody(ENTER_CHAT, body, Wrong_Key, null);
    }
}