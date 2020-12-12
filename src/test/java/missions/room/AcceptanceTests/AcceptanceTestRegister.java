package missions.room.AcceptanceTests;

import missions.room.AcceptanceTestDataObjects.UserTestData;
import missions.room.AcceptanceTestDataObjects.RegisterDetailsTest;
import missions.room.AcceptanceTestDataObjects.UserTypeTest;
import missions.room.AcceptanceTestsBridge.AcceptanceTestsProxyBridge;
import missions.room.Managers.RegisterManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * use case 2.2 - Register
 */
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class AcceptanceTestRegister extends AcceptanceTest{
    private RegisterDetailsTest registerDetailsTest;

    @BeforeEach
    public void setup(){
        TestDataInitializer.getTestDataFromCsvReport();
        setUpCSV();
        UserTestData user = AcceptanceTest.students.get(1);
        this.registerDetailsTest = new RegisterDetailsTest(
                    user.getfName(), user.getlName(), "111", user.getAlias(), UserTypeTest.Student, "A");
    }

    @Test
    public void testRegisterSuccess(){
        boolean isRegistered = register(registerDetailsTest);
        assertTrue(isRegistered);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        testRegisterSuccess();
        boolean isRegistered = register(registerDetailsTest);
        assertFalse(isRegistered);
    }

    @Test
    public void testRegisterFailRegisterAsTeacher(){

    }

    @AfterEach
    public void tearDown(){

    }



}
