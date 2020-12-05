package missions.room.LogicManagerTests;

import Data.Data;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.LogicManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class LogicManagerTestsRealMailSenderStudentRepo extends LogicManagerRealMailSender {

    @BeforeEach
    void SetUp(){
        super.setUp();
        Field aliasToCode= null;
        try {
            aliasToCode = LogicManager.class.getDeclaredField("aliasToCode");
            aliasToCode.setAccessible(true);
            ((ConcurrentHashMap)aliasToCode.get(logicManager)).clear();
        } catch (NoSuchFieldException | IllegalAccessException e) {
           fail();
        }

    }
    @Override
    void setUpMocks(){
    }

    @Test
    void testRegisterInvalidNotExistInDBStudent(){
        registerSetUp();
        checkWrongRegister(Data.WRONG_NAME, OpCode.Not_Exist);
        registerTearDown();
    }

    @Test
    void testRegisterInvalidAlreadyRegisteredStudent(){
        loginSetUp();
        Response<Boolean> response=logicManager.register(dataGenerator.getRegisterDetails(Data.VALID));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), OpCode.Already_Exist);
        loginTearDown();
    }
}
