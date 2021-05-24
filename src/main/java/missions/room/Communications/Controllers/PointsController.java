package missions.room.Communications.Controllers;

import DataObjects.APIObjects.ApiKey;
import DataObjects.APIObjects.ReducePointsData;
import DataObjects.FlatDataObjects.Response;
import missions.room.Service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that this class is a Controller
@RequestMapping(path="/points")
public class PointsController extends AbsController{

    @Autowired
    private PointsService pointsService;

    @PostMapping("/view")
    public Response<?> watchTable(@RequestBody String apiKeyData){
        ApiKey apiKey= json.fromJson(apiKeyData, ApiKey.class);
        return pointsService.watchTable(apiKey.getApiKey());
    }

    @PostMapping("/reduce")
    public Response<?> reducePoints(@RequestBody String reducePointsDataStr){
        ReducePointsData reducePointsData= json.fromJson(reducePointsDataStr, ReducePointsData.class);
        return pointsService.reducePoints(reducePointsData.getApiKey(),reducePointsData.getAlias(),reducePointsData.getPoints());
    }
}
