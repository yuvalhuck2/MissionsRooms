package missions.room.Communications.Controllers;

import Data.Data;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

@RunWith(SpringRunner.class)
class ChatControllerTest extends ControllerTest {

    @Test
    void testSendMessage() {
        String body = gson.toJson(dataGenerator.getChatMessageData(Data.VALID));
        testControllerWithBody(SEND_CHAT_MESSAGE, body, Wrong_Key, "");
    }

    @Test
    void testEnterChat() {
        String body = gson.toJson(dataGenerator.getNewRoomDetails(Data.Valid_Student));
        testControllerWithBody(ENTER_CHAT, body, Wrong_Key, null);
    }
}