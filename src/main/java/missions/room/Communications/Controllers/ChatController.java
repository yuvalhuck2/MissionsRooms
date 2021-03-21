package missions.room.Communications.Controllers;


import DataAPI.MessageChatDetails;
import DataAPI.MessageData;
import DataAPI.Response;
import missions.room.Managers.ChatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that this class is a Controller
@RequestMapping(path="/chat")
public class ChatController extends AbsController {

    @Autowired
    private ChatManager chatManager;


    @PostMapping("/send")
    public Response<?> sendMessage(@RequestBody String messageChatDetails){
        MessageChatDetails messageData= json.fromJson(messageChatDetails, MessageChatDetails.class);
        return chatManager.sendMessage(messageData.getApiKey(),messageData.getChatMessageData(),messageData.getRoomId());
    }

    @PostMapping("/enter")
    public Response<?> enterChat(@RequestBody String apiKey){
        return chatManager.enterChat(apiKey);
    }

}
