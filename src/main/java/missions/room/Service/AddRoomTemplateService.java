package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import DataAPI.RoomTemplateDetailsData;
import missions.room.Managers.AddRoomTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class AddRoomTemplateService {

    @Autowired
    private AddRoomTemplateManager addRoomTemplateManager;

    /**
     * req 4.4 - add room template
     * @param auth - authentication object
     * @param details - details of the template to be created
     * @return if the template was added successfully
     */
    public Response<Boolean> createRoomTemplate(Auth auth, RoomTemplateDetailsData details){
        return addRoomTemplateManager.createRoomTemplate(auth.getApiKey(),details);
    }

}
