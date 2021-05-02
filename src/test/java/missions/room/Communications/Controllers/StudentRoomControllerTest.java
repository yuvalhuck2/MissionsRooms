package missions.room.Communications.Controllers;

import DataObjects.APIObjects.AnswerDeterministicData;
import DataObjects.APIObjects.StoryAnswerData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.INVALID_ROOM_ID;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

class StudentRoomControllerTest extends ControllerTest {

    @Test
    void watchRoomDetails() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(WATCH_STUDET_ROOMS, body, Wrong_Key, null);
    }

    @Test
    void watchRoomData() {
        String body = gson.toJson(apiKeyAndAliasData);
        testControllerWithBody(WATCH_ROOM_DATA, body, Wrong_Key, null);
    }

    @Test
    void disconnectFromRoom() {
        String body = gson.toJson(apiKeyAndAliasData);
        given().
                webAppContextSetup(webApplicationContext).
                contentType(ContentType.JSON).
                body(body).
                when().
                post(DISCONNECT_FROM_ROOM);
    }

    @Test
    void answerDeterministicQuestion() {
        AnswerDeterministicData answerDeterministicData = new AnswerDeterministicData("apiKey", "roomId", true);
        String body = gson.toJson(answerDeterministicData);
        testControllerWithBody(SOLVE_DETERMINISTIC, body, Wrong_Key, null);
    }

    @Test
    void answerOpenQuestion() {
        given().
                webAppContextSetup(webApplicationContext).
                contentType(ContentType.JSON).
                param("missionId","missionId").
                param("roomId","roomId").
                param("token", "apiKey").
                when().
                post(SOLVE_OPEN_QUESTION).
                then().
                statusCode(201).
                body(reasonParamName, equalTo(INVALID_ROOM_ID.toString())).
                body(valueParamName, equalTo(false));
    }

    @Test
    void answerStory() {
        StoryAnswerData answerData = new StoryAnswerData("apiKey", "roomId", "sentence\n");
        String body = gson.toJson(answerData);
        testControllerWithBody(SOLVE_DETERMINISTIC, body, Wrong_Key, false);
    }

    @Test
    void finishStory() {
        String body = gson.toJson(roomIdAndApiKey);
        testControllerWithBody(SOLVE_DETERMINISTIC, body, Wrong_Key, null);
    }

    //TODO trivia related
}