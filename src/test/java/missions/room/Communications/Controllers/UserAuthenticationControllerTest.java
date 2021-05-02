package missions.room.Communications.Controllers;

import Data.Data;
import org.junit.jupiter.api.Test;

import static Data.APIPaths.*;
import static DataObjects.FlatDataObjects.OpCode.*;

class UserAuthenticationControllerTest extends ControllerTest {

    @Test
    void testRegister() {
        String body = gson.toJson(dataGenerator.getRegisterDetails(Data.VALID));
        testControllerWithBody(REGISTER, body, Not_Exist, null);
    }

    @Test
    void testRegisterCode(){
        String body = gson.toJson(dataGenerator.getRegisterDetails(Data.VALID));
        testControllerWithBody(REGISTER_CODE, body, Wrong_Code, false);
    }

    @Test
    void testLogin(){
        String body = gson.toJson(dataGenerator.getRegisterDetails(Data.VALID));
        testControllerWithBody(LOGIN, body, Not_Exist, null);
    }

    @Test
    void testChangePassword(){
        String body = gson.toJson(dataGenerator.getRegisterDetails(Data.VALID));
        testControllerWithBody(CHANGE_PASSWORD, body, Not_Exist, false);
    }

    @Test
    void testResetPassword(){
        String body = gson.toJson(dataGenerator.getRegisterDetails(Data.VALID));
        testControllerWithBody(RESET_PASSWORD, body, Not_Exist, false);
    }


}