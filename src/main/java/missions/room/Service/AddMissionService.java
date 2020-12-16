package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import missions.room.Managers.AddMissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddMissionService {

    @Autowired
    private AddMissionManager addMissionManager;

    /**
     * req 4.5 - add mission
     * @param auth - authentication object
     * @param missionData - details of the mission
     * @return if the mission was added successfully
     */
    public Response<Boolean> createMission(Auth auth, String missionData){
        return addMissionManager.addMission(auth.getApiKey(),missionData);

    }

}
