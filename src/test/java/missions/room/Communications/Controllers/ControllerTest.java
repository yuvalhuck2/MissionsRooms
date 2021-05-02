package missions.room.Communications.Controllers;

import Data.DataGenerator;
import DataObjects.APIObjects.ApiKeyAndAliasData;
import DataObjects.FlatDataObjects.OpCode;
import DataObjects.APIObjects.RoomIdAndApiKeyData;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ControllerTest {

    protected static String reasonParamName = "reason";
    protected static String valueParamName = "value";
    protected static ApiKeyAndAliasData apiKeyAndAliasData = new ApiKeyAndAliasData("apiKey", "alias");
    protected static RoomIdAndApiKeyData roomIdAndApiKey = new RoomIdAndApiKeyData("apiKey", "roomId");

    @Autowired
    protected WebApplicationContext webApplicationContext;
    protected DataGenerator dataGenerator;
    protected Gson gson;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        dataGenerator = new DataGenerator();
        gson = new Gson();
    }

    protected void testControllerWithBody(String path, String body, OpCode opCode, Object value) {
        given().
                webAppContextSetup(webApplicationContext).
                contentType(ContentType.JSON).
                body(body).
                when().
                post(path).
                then().
                statusCode(200).
                body(reasonParamName, equalTo(opCode.toString())).
                body(valueParamName, equalTo(value));
    }

    protected void testControllerNoBody(String path, OpCode opCode, Object value) {
        given().
                webAppContextSetup(webApplicationContext).
                contentType(ContentType.JSON).
                when().
                post(path).
                then().
                statusCode(200).
                body(reasonParamName, equalTo(opCode.toString())).
                body(valueParamName, equalTo(value));
    }
}
