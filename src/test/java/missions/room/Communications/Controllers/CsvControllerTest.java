package missions.room.Communications.Controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static Data.APIPaths.UPLOAD_CSV;
import static DataObjects.FlatDataObjects.OpCode.Wrong_Key;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
class CsvControllerTest extends ControllerTest {

    @Test
    void uploadFileTest() {
        given().multiPart("files",new ArrayList<>()).
                webAppContextSetup(webApplicationContext).
                contentType(ContentType.JSON).
                param("token", "apiKey").
                when().
                post(UPLOAD_CSV).
                then().
                body(reasonParamName, equalTo(Wrong_Key.toString())).
                body(valueParamName, equalTo(false));
    }
}