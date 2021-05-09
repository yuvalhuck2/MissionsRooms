package missions.room.AcceptanceTests.AcceptanceTests;

import missions.room.AcceptanceTests.AcceptanceTestDataObjects.*;
import missions.room.AcceptanceTests.ExternalSystemMocks.MailSenderAlwaysFalseMock;
import missions.room.AcceptanceTests.ExternalSystemMocks.MailSenderAlwaysTrueMock;
import missions.room.AcceptanceTests.ExternalSystemMocks.VerificationCodeGeneratorMock;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

/**
 * use case 2.2 - Register
 */
@TestInstance(PER_CLASS)
@SpringBootTest
@Sql(scripts = "/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class AcceptanceTestRegisterAndCreateRoom extends AcceptanceTest{
    private RegisterDetailsTest goodRegisterDetailsTest0; // in CSV
    private RegisterDetailsTest goodRegisterDetailsTest1;
    private RegisterDetailsTest goodRegisterDetailsTest2;
    private RegisterDetailsTest badRegisterDetailsTest; // not in CSV
    private RegisterDetailsTest teacherRegisterDetails;
    private RoomTemplateDetailsTestData roomTemplateDetailsTestData;
    private RoomDetailsTest roomDetailsTest;


    @BeforeAll
    public void setup(){
        setUpCSV();
        UserTestData user0 = AcceptanceTest.students.get(0);
        UserTestData user1 = AcceptanceTest.students.get(1);
        UserTestData user2 = AcceptanceTest.students.get(2);
        UserTestData teacher = AcceptanceTest.teacher.get(0);

        this.teacherRegisterDetails = new RegisterDetailsTest(teacher.getAlias(), teacher.getAlias());
        bridge.register(teacherRegisterDetails);

        this.goodRegisterDetailsTest0 = new RegisterDetailsTest(user0.getAlias(), user0.getAlias());
        this.goodRegisterDetailsTest1 = new RegisterDetailsTest(user1.getAlias(), user1.getAlias());
        this.goodRegisterDetailsTest2 = new RegisterDetailsTest(user2.getAlias(), user2.getAlias());
        this.badRegisterDetailsTest = new RegisterDetailsTest("error", "error");

        this.roomTemplateDetailsTestData = AcceptanceTest.templatesData.get(0);

        this.roomDetailsTest = AcceptanceTest.roomDetails.get(0);
    }

    public void setUpAddRoom(){
        bridge.setExternalSystems(new MailSenderAlwaysTrueMock(), new VerificationCodeGeneratorMock());
        register(teacherRegisterDetails);
        registerCodeTeacher(teacherRegisterDetails.getAlias(), this.testVerificationCode);
        teacherLogin(teacherRegisterDetails.getAlias(), teacherRegisterDetails.getAlias());
        createTemplate(roomTemplateDetailsTestData);
    }

    @Test
    public void testRegisterSuccess(){
        bridge.setExternalSystems(new MailSenderAlwaysTrueMock(), new VerificationCodeGeneratorMock());
        boolean isRegistered = register(goodRegisterDetailsTest1);
        assertTrue(isRegistered);
        boolean isRegisteredCode = registerCode(goodRegisterDetailsTest1.getAlias(), this.testVerificationCode, teacherRegisterDetails.getAlias());
        assertTrue(isRegisteredCode);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        bridge.setExternalSystems(new MailSenderAlwaysTrueMock(), new VerificationCodeGeneratorMock());
        boolean isRegistered = register(goodRegisterDetailsTest2);
        assertTrue(isRegistered);
        boolean isRegisteredCode = registerCode(goodRegisterDetailsTest2.getAlias(), this.testVerificationCode, teacherRegisterDetails.getAlias());
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
        boolean isRegisteredCode = registerCode(goodRegisterDetailsTest0.getAlias(), "wrong-code", teacherRegisterDetails.getAlias());
        assertFalse(isRegisteredCode);

    }

    @Test
    public void testRegisterFailBadMailSender(){
        bridge.setExternalSystems(new MailSenderAlwaysFalseMock(), null);
        boolean isRegistered = register(goodRegisterDetailsTest0);
        assertFalse(isRegistered);

    }

    @Test
    public void testAddRoomSuccess(){
        setUpAddRoom();
        assertTrue(createRoom(roomDetailsTest));

        RoomDetailsTest answerDetailsTest = watchRoomDetails();
        assertEquals(roomDetailsTest, answerDetailsTest);
        assertEquals(3, answerDetailsTest.getNumberOfMissions());
        MissionDetailsTest m = AcceptanceTest.missionData.get(0);
        assertEquals(answerDetailsTest.getCurrentMission(), AcceptanceTest.missionData.get(0));
    }

    @Test
    public void testAddRoomWrongName(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setRoomName("");
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }

    @Test
    public void testAddRoomNullName(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setRoomName(null);
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }

    @Test
    public void testAddRoomWrongBonus(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setBonus(-1);
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }

    @Test
    public void testAddRoomWrongParticipant(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setParticipant("");
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }

    @Test
    public void testAddRoomNullParticipant(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setParticipant(null);
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }

    @Test
    public void testAddRoomWrongParticipantType(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setParticipantType(null);
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }

    @Test
    public void testAddRoomNotMatchParticipantAndParticipantType(){
        setUpAddRoom();
        RoomDetailsTest roomDetailsTest1 = new RoomDetailsTest(roomDetailsTest);
        roomDetailsTest1.setParticipantType(ParticipantTypeTest.Personal);
        assertFalse(createRoom(roomDetailsTest1));
        assertNull(watchRoomDetails());
    }




}
