package missions.room.Communications.Controllers;


import DataObjects.APIObjects.ApiKey;
import DataObjects.APIObjects.SuggestionData;
import DataObjects.FlatDataObjects.Response;
import missions.room.Service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/view")
    public Response<?> viewSuggestions(@RequestBody String apiKey) {
        ApiKey data = json.fromJson(apiKey, ApiKey.class);
        Response<List<SuggestionData>> response = suggestionService.watchSuggestions(data.getApiKey());
        return response;
    }

    @PostMapping("/delete")
    public Response<?> deleteSuggestion(@RequestBody String suggestionData) {
        SuggestionData data = json.fromJson(suggestionData, SuggestionData.class);
        Response<Boolean> response = suggestionService.deleteSuggestion(data.getApiKey(),data.getId());
        return response;
    }
}
