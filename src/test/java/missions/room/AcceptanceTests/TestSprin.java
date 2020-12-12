package missions.room.AcceptanceTests;

import static org.junit.Assert.*;

import Data.Data;
import DataAPI.OpCode;
import ExternalSystemMocks.MailSenderTrueMock;
import ExternalSystems.MailSender;
import missions.room.Managers.RegisterManager;
import missions.room.RegisterManagerTests.RegisterManagerTestsAllStubs;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class TestSprin extends AcceptanceTest {


    @BeforeEach
    void SetUp(){

    }


    @Test
    void testRegisterInvalidNotExistInDBStudent(){
        assertNotNull(r);
    }
}
