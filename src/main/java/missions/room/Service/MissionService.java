package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import missions.room.Managers.MissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionService {

    @Autowired
    private MissionManager missionManager;

    /**
     * req 4.5 - add mission
     * @param auth - authentication object
     * @param missionData - details of the mission
     * @return if the mission was added successfully
     */
    public Response<Boolean> createMission(Auth auth, String missionData){
        return missionManager.addMission(auth.getApiKey(),missionData);

    }

}
