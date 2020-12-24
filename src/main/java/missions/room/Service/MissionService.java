package missions.room.Service;

import DataAPI.Auth;
import DataAPI.MissionData;
import DataAPI.MissionFilterData;
import DataAPI.Response;
import missions.room.Managers.MissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

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

    /**
     * req 4.3 - search missions
     * @param auth - authentication object
     * @return - list of the missions were filtered
     */
    public Response<List<MissionData>> searchMissions(Auth auth){
        return missionManager.searchMissions(auth.getApiKey());
    }

}
