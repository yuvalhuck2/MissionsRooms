package missions.room.Service;

import DataObjects.APIObjects.MessageData;
import DataObjects.FlatDataObjects.Response;
import DataObjects.FlatDataObjects.UserProfileData;
import missions.room.Managers.ProfileMessagesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileMessagesService {

    @Autowired
    private ProfileMessagesManager profileMessagesManager;

    /**
     * req 3.4.1 - send message
     * @param apiKey - authentication object
     * @param message - message to send to the other user
     * @param alias - target user alias
     * @return if the message was sent successfully
     */
    public Response<Boolean> sendMessage(String apiKey, String message, String alias){
        return profileMessagesManager.sendMessage(apiKey,message,alias);
    }

    /**
     * req 3.4.2 - view my messages
     * @param apiKey - authentication object
     * @return messages list
     */
    public Response<List<MessageData>> viewMessages(String apiKey){
        return profileMessagesManager.viewMessages(apiKey);
    }

    /**
     * req 3.4.2 - delete a message
     * @param apiKey - authentication object
     * @param id - message id
     * @return if the message was deleted successfully
     */
    public Response<?> deleteMessage(String apiKey, String id) {
        return profileMessagesManager.deleteMessage(apiKey,id);
    }

    /**
     * req 3.5 - watch user profile
     * @param apiKey - authentication object
     * @return user profile details
     */
    public Response<List<UserProfileData>> watchProfile(String apiKey){
        return profileMessagesManager.watchProfile(apiKey);
    }




}
