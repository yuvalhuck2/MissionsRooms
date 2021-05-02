package missions.room.Communications.Controllers;

import DataObjects.APIObjects.SuggestionData;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;

class SuggestionControllerTest extends ControllerTest {

    private static final SuggestionData suggestionData =
            new SuggestionData("apiKey", "suggestion", "id");

    @Test
    void addSuggestion() {
        String body = gson.toJson(suggestionData);
        testControllerWithBody(ADD_SUGGESTION, body, Wrong_Key, false);
    }

    @Test
    void viewSuggestions() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(WATCH_SUGGESTIONS, body, Wrong_Key, null);
    }

    @Test
    void deleteSuggestion() {
        String body = gson.toJson(suggestionData);
        testControllerWithBody(DELETE_SUGGESTION, body, Wrong_Key, null);
    }
}