package missions.room.Service;

import DataAPI.RecordTableData;
import DataAPI.Response;
import DataAPI.RoomType;
import missions.room.Managers.PointsManager;

public class PointsService {

    private PointsManager pointsManager;

    /**
     * req 3.1 - watch rank Table details
     * @param apiKey - authentication object
     * @return the record table
     */
    public Response<RecordTableData> watchTable (String apiKey){
        return pointsManager.watchTable(apiKey);
    }

    /**
     * req 4.13 - deduce points to a student
     * @param apiKey - authentication object
     * @param studentAlias - the identifier of the student need to deduce points to.
     * @param pointsToDeduce - the amount of points to deduce the user
     * @return if the points were deducted successfully
     */
    public Response<Boolean> deducePoints(String apiKey,String studentAlias, int pointsToDeduce){
        return deducePoints(apiKey, studentAlias, pointsToDeduce);
    }
}
