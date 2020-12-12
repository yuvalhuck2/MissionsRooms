package missions.room.AcceptanceTests.AcceptanceTests;

import missions.room.AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.RegisterDetailsTest;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.UserTypeTest;
import org.junit.jupiter.api.*;
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
