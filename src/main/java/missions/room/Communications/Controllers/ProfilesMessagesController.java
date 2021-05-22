package missions.room.Communications.Controllers;

import DataAPI.ApiKey;
import DataAPI.MessageData;
import DataAPI.Response;
import DataAPI.TransferDetailsData;
import missions.room.Service.ProfileMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that this class is a Controller
@RequestMapping(path="/profile")
public class ProfilesMessagesController extends AbsController{

    @Autowired
    private ProfileMessagesService profileMessagesService;

    @PostMapping("message/send")
    public Response<?> sendMessage(@RequestBody String message){
        MessageData messageData= json.fromJson(message, MessageData.class);
        return profileMessagesService.sendMessage(messageData.getApiKey(),messageData.getMessage(),messageData.getTarget());
    }

    @PostMapping("message/view")
    public Response<?> viewMessages(@RequestBody String apiKeyData){
        ApiKey apiKey= json.fromJson(apiKeyData, ApiKey.class);
        return profileMessagesService.viewMessages(apiKey.getApiKey());
    }

    @PostMapping("message/delete")
    public Response<?> deleteMessage(@RequestBody String message){
        MessageData messageData= json.fromJson(message, MessageData.class);
        return profileMessagesService.deleteMessage(messageData.getApiKey(),messageData.getId());
    }

    @PostMapping("/view")
    public Response<?> watchProfile(@RequestBody String message){
        ApiKey apiKey= json.fromJson(message, ApiKey.class);
        return profileMessagesService.watchProfile(apiKey.getApiKey());
    }




}
