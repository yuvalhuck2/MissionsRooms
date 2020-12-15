package missions.room.AcceptanceTests.AcceptanceTests;

import ch.qos.logback.core.spi.LifeCycle;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.RegisterDetailsTest;
import missions.room.AcceptanceTests.AcceptanceTestDataObjects.UserTypeTest;
import missions.room.AcceptanceTests.ExternalSystemMocks.MailSenderAlwaysFalseMock;
import missions.room.AcceptanceTests.ExternalSystemMocks.MailSenderAlwaysTrueMock;
import missions.room.AcceptanceTests.ExternalSystemMocks.VerificationCodeGeneratorMock;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * use case 2.2 - Register
 */
@TestInstance(PER_CLASS)
@SpringBootTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
public class AcceptanceTestRegister extends AcceptanceTest{
    private RegisterDetailsTest goodRegisterDetailsTest0; // in CSV
    private RegisterDetailsTest goodRegisterDetailsTest1;
    private RegisterDetailsTest goodRegisterDetailsTest2;
    private RegisterDetailsTest badRegisterDetailsTest; // not in CSV

    @BeforeAll
    public void setup(){
        setUpCSV();
        UserTestData user0 = AcceptanceTest.students.get(0);
        UserTestData user1 = AcceptanceTest.students.get(1);
        UserTestData user2 = AcceptanceTest.students.get(2);

        this.goodRegisterDetailsTest0 = new RegisterDetailsTest(user0.getAlias(), user0.getAlias());
        this.goodRegisterDetailsTest1 = new RegisterDetailsTest(user1.getAlias(), user1.getAlias());
        this.goodRegisterDetailsTest2 = new RegisterDetailsTest(user2.getAlias(), user2.getAlias());
        this.badRegisterDetailsTest = new RegisterDetailsTest("error", "error");
    }

    @Test
    public void testRegisterSuccess(){
        bridge.setExternalSystems(new MailSenderAlwaysTrueMock(), new VerificationCodeGeneratorMock());
        boolean isRegistered = register(goodRegisterDetailsTest1);
        assertTrue(isRegistered);
        boolean isRegisteredCode = registerCode(goodRegisterDetailsTest1.getAlias(), this.testVerificationCode);
        assertTrue(isRegisteredCode);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        bridge.setExternalSystems(new MailSenderAlwaysTrueMock(), new VerificationCodeGeneratorMock());
        boolean isRegistered = register(goodRegisterDetailsTest2);
        assertTrue(isRegistered);
        boolean isRegisteredCode = registerCode(goodRegisterDetailsTest2.getAlias(), this.testVerificationCode);
        assertTrue(isRegisteredCode);
        isRegistered = register(goodRegisterDetailsTest2);
        assertFalse(isRegistered);
    }

    @Test
    public void testRegisterFailNotInCsv(){
        boolean isRegistered = register(badRegisterDetailsTest);
        assertFalse(isRegistered);

    }

    @Test
    public void testRegisterFailWrongCode(){
        bridge.setExternalSystems(new MailSenderAlwaysTrueMock(), new VerificationCodeGeneratorMock());
        boolean isRegistered = register(goodRegisterDetailsTest0);
        assertTrue(isRegistered);
        boolean isRegisteredCode = registerCode(goodRegisterDetailsTest0.getAlias(), "wrong-code");
        assertFalse(isRegisteredCode);

    }

    @Test
    public void testRegisterFailBadMailSender(){
        bridge.setExternalSystems(new MailSenderAlwaysFalseMock(), null);
        boolean isRegistered = register(goodRegisterDetailsTest0);
        assertFalse(isRegistered);

    }



    @AfterAll
    public void tearDown(){
        //TODO: delete the students
    }



}
