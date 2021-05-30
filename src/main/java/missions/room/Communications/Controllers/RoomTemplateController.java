package missions.room.Communications.Controllers;

import DataObjects.APIObjects.ApiKey;
import DataObjects.APIObjects.RoomTemplateDetailsData;
import DataObjects.APIObjects.RoomTemplateForSearch;
import DataObjects.FlatDataObjects.Response;
import missions.room.Service.RoomTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // This means that this class is a Controller
@RequestMapping(path="/template") // This means URL's start with /demo (after Application path)
public class RoomTemplateController extends AbsController{

    @Autowired
    private RoomTemplateService roomTemplateService;

    public RoomTemplateController() {
        super();
    }

    @PostMapping("/create")
    public Response<?> creatRoomTemplate(@RequestBody String createTemplateData) {
        RoomTemplateDetailsData data = json.fromJson(createTemplateData, RoomTemplateDetailsData.class);
        Response<Boolean> response = roomTemplateService.createRoomTemplate(data.getApiKey(),data);
        return response;
    }

    @PostMapping("/search")
    public Response<?> searchTemplate(@RequestBody String apiKey) {
        ApiKey data = json.fromJson(apiKey, ApiKey.class);
        Response<List<RoomTemplateForSearch>> response = roomTemplateService.searchRoomTemplates(data.getApiKey());
        return response;
    }

}
