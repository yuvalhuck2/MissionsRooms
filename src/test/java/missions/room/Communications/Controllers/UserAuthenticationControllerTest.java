package missions.room.Communications.Controllers;

import DataAPI.OpCode;
import DataAPI.RegisterDetailsData;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;
import com.google.gson.Gson;


import static io.restassured.module.webtestclient.RestAssuredWebTestClient.given;
import static org.hamcrest.Matchers.equalTo;

class UserAuthenticationControllerTest {

    @Test
    void greeting_controller_returns_json_greeting() {
        given().
                standaloneSetup(new UserAuthenticationController())
                .contentType(ContentType.JSON).
                body(new Gson().toJson(new RegisterDetailsData("yuval","sabag"))).

                when().
                post("/UserAuth").
                then().
                statusCode(200).
                body("reason", equalTo(OpCode.Not_Exist)).
                body("value", equalTo(null));
    }
}