package missions.room.Service;

import DataObjects.FlatDataObjects.RecordTableData;
import DataObjects.FlatDataObjects.Response;
import missions.room.Managers.PointsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointsService {

    @Autowired
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
     * req 4.13 - reduce points to a student
     * @param apiKey - authentication object
     * @param studentAlias - the identifier of the student need to deduce points to.
     * @param pointsToDeduce - the amount of points to deduce the user
     * @return if the points were deducted successfully
     */
    public Response<Boolean> reducePoints(String apiKey, String studentAlias, int pointsToDeduce){
        return pointsManager.reducePoints(apiKey, studentAlias, pointsToDeduce);
    }
}
