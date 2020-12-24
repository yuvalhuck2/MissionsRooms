package missions.room.Service;

import DataAPI.*;
import missions.room.Managers.RoomTemplateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
public class RoomTemplateService {

    @Autowired
    private RoomTemplateManager roomTemplateManager;

    /**
     * req 4.4 - add room template
     * @param auth - authentication object
     * @param details - details of the template to be created
     * @return if the template was added successfully
     */
    public Response<Boolean> createRoomTemplate(Auth auth, RoomTemplateDetailsData details){
        return roomTemplateManager.createRoomTemplate(auth.getApiKey(),details);
    }

    /**
     * req 4.14 - look for room templates
     * @param auth - authentication object
     * @return list of filtered room templates
     */
    public Response<List<RoomTemplateForSearch>> searchRoomTemplates(Auth auth){
        return roomTemplateManager.searchRoomTemplates(auth.getApiKey());
    }

}
