package missions.room.Communications.Controllers;

import DataAPI.*;
import com.google.gson.Gson;
import missions.room.Service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/unApprovedAnswers")
    public Response<?> getUnApproveAnswers(@RequestParam String apiKey, @RequestParam(name = "roomId") String roomId) {
        Response<RoomOpenAnswerData> res = missionService.watchSolutions(apiKey, roomId);
        return res;
    }

}
