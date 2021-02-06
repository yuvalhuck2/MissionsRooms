package missions.room.Communications.Controllers;

import DataAPI.ApiKey;
import DataAPI.CreateMissionData;
import DataAPI.MissionData;
import DataAPI.Response;
import com.google.gson.Gson;
import missions.room.Service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/mission") // This means URL's start with /demo (after Application path)
public class MissionController extends AbsController {

    @Autowired
    private MissionService missionService;

    public MissionController() {
        super();
    }

    @PostMapping("/create")
    public Response<?> creatMission(@RequestBody String createMissionData) {
        CreateMissionData data = json.fromJson(createMissionData, CreateMissionData.class);
        Response<Boolean> response = missionService.createMission(data.getApiKey(),data.getMissionData());
        return response;
    }

    @PostMapping("/search")
    public Response<?> searchMissions(@RequestBody String apiKey) {
        ApiKey data = json.fromJson(apiKey, ApiKey.class);
        Response<List<MissionData>> response = missionService.searchMissions(data.getApiKey());
        return response;
    }

}
