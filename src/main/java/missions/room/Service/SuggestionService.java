package missions.room.Service;

import DataAPI.Auth;
import DataAPI.Response;
import missions.room.Managers.SuggestionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class SuggestionService {

    @Autowired
    private SuggestionManager suggestionManager;

    /**
     * req 3.7 - add suggestion
     * @param apiKey - authentication object
     * @param suggestion - suggestion to a mission the student want to send
     * @return if the suggestion was added successfully
     */
    public Response<Boolean> addSuggestion(String apiKey, String suggestion){
        return suggestionManager.addSuggestion(apiKey,suggestion);
    }
}
