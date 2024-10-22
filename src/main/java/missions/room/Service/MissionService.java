package missions.room.Service;

import DataObjects.APIObjects.RoomOpenAnswerData;
import DataObjects.FlatDataObjects.MissionData;
import DataObjects.FlatDataObjects.Response;
import missions.room.Managers.MissionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class MissionService {

    @Autowired
    private MissionManager missionManager;

    /**
     * req 4.5 - add mission
     * @param apiKey - authentication object
     * @param missionData - details of the mission
     * @return if the mission was added successfully
     */
    public Response<Boolean> createMission(String apiKey, String missionData){
        return missionManager.addMission(apiKey,missionData);

    }

    /**
     * req 4.3 - search missions
     * @param apiKey - authentication object
     * @return - list of the missions were filtered
     */
    public Response<List<MissionData>> searchMissions(String apiKey ){
        return missionManager.searchMissions(apiKey);
    }


    /**
     * req 4.9 - watch students solutions
     * @return all the solutions that wait to be approved
     */
    public Response<RoomOpenAnswerData> watchSolutions(String apiKey, String roomId){
        return missionManager.watchSolutions(apiKey, roomId);
    }

    public Response<File> getMissionOpenAnswerFile(String apiKey, String roomId, String missionId) {
        return missionManager.getMissionOpenAnswerFile(apiKey, roomId, missionId);
    }
}
