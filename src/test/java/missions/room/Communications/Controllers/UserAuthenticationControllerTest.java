package missions.room.Communications.Controllers;

import DataAPI.OpCode;
import DataAPI.RegisterDetailsData;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;


import static io.restassured.module.webtestclient.RestAssuredWebTestClient.given;
import static org.hamcrest.Matchers.equalTo;

class UserAuthenticationControllerTest {

    @Test
    void greeting_controller_returns_json_greeting() {
        given().
                standaloneSetup(new UserAuthenticationController()).
                body("registerCodeDetailsDataStr", (ObjectMapper) new RegisterDetailsData("yuval","s")).
//                body("alias" , ObjectMapperType.valueOf("yuval")).
//                body("password" , ObjectMapperType.valueOf("pasword")).
                when().
                post("/UserAuth").
                then().
                statusCode(200).
                body("reason", equalTo(OpCode.Not_Exist)).
                body("value", equalTo(null));
    }
}