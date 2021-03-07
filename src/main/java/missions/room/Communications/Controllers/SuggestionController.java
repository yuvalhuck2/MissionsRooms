package missions.room.Communications.Controllers;


import DataAPI.Response;
import DataAPI.RoomTemplateDetailsData;
import DataAPI.SuggestionData;
import missions.room.Service.RoomTemplateService;
import missions.room.Service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // This means that this class is a Controller
@RequestMapping(path="/suggestion") // This means URL's start with /demo (after Application path)
public class SuggestionController extends AbsController {

    @Autowired
    private SuggestionService suggestionService;

    public SuggestionController() {
        super();
    }

    @PostMapping("/add")
    public Response<?> addSuggestion(@RequestBody String suggestionData) {
        SuggestionData data = json.fromJson(suggestionData, SuggestionData.class);
        Response<Boolean> response = suggestionService.addSuggestion(data.getApiKey(),data.getSuggestion());
        return response;
    }
}
