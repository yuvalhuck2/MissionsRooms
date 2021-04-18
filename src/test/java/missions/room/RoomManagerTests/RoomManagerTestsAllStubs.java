package missions.room.RoomManagerTests;

import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import DataAPI.ClassRoomData;
import DataAPI.OpCode;
import DataAPI.OpenAnswerResponseMessages;
import DataAPI.Response;
import DomainMocks.MockRam;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryExceptionSave;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomRepositoryExceptionFindParticipantRoomMock;
import RepositoryMocks.RoomRepository.RoomRepositoryTimeOutExceptionFindParticipantRoomMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryExceptionFindByIdMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Communications.Publisher.SinglePublisher;
import missions.room.Domain.Notifications.Notification;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Rooms.StudentRoom;
import missions.room.Domain.Users.Teacher;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.RoomManager;
import missions.room.Repo.RoomRepo;
import missions.room.Repo.TeacherRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoomManagerTestsAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    protected RoomCrudRepository roomCrudRepository;

    @Autowired
    protected RoomManager roomManger;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected ClassroomRepository classroomRepository;

    protected DataGenerator dataGenerator;

    protected String apiKey;

    protected Ram ram;

    protected Room openAnsRoom;

    protected Room openAnsRoomMultiple;

    protected String studentApiKey;

    protected final String invalidSuffix = "$";

    /**
     * working with mockitos
     */

    @InjectMocks
    protected RoomManager roomMangerWithMockito;

    @Mock
    protected Ram mockRam;

    @Mock
    protected TeacherRepo mockTeacherRepo;

    @Mock
    protected TeacherCrudRepository mockTeacherCrudRepository;

    @Mock
    protected RoomRepo mockRoomRepo;

    private AutoCloseable closeable;

    /**
     * working with mockitos
     */

    @BeforeEach
    void setUp() {
        dataGenerator = new DataGenerator();
        apiKey="key";
        studentApiKey = "studentKey";
        initMocks();
    }

    protected void initMocks() {
        closeable= MockitoAnnotations.openMocks(this);
        Teacher teacher=dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM);
        String teacherAlias=dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getAlias();
        this.openAnsRoom = dataGenerator.getRoom(Data.VALID_OPEN_ANS);
        this.openAnsRoomMultiple = dataGenerator.getRoom(Data.VALID_OPEN_ANS2);

        when(mockRam.getAlias(apiKey))
                .thenReturn(teacherAlias);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_TEACHER_KEY))
                .thenReturn(WRONG_TEACHER_NAME);
        when(mockTeacherRepo.findTeacherById(teacherAlias))
                .thenReturn(new Response<>(teacher,OpCode.Success));
        when(mockRoomRepo.findRoomById(this.openAnsRoom.getRoomId()))
                .thenReturn(new Response<Room>(this.openAnsRoom, OpCode.Success));
        when(mockRoomRepo.findRoomById(this.openAnsRoomMultiple.getRoomId()))
                .thenReturn(new Response<Room>(this.openAnsRoomMultiple, OpCode.Success));
        when(mockRam.getRoom(this.openAnsRoom.getRoomId()))
                .thenReturn(this.openAnsRoom);
        when(mockRam.getRoom(this.openAnsRoomMultiple.getRoomId()))
                .thenReturn(this.openAnsRoomMultiple);
        when(mockRoomRepo.deleteRoom(this.openAnsRoomMultiple))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockRam.getApiKey(dataGenerator.getStudent(Data.VALID).getAlias()))
                .thenReturn(studentApiKey);
        when(mockRoomRepo.save(this.openAnsRoomMultiple))
                .thenReturn(new Response<>(this.openAnsRoomMultiple, OpCode.Success));
        when(mockRoomRepo.deleteRoom(this.openAnsRoom))
                .thenReturn(new Response<>(true, OpCode.Success));
        when(mockRoomRepo.findRoomById(this.openAnsRoom.getRoomId() + invalidSuffix))
                .thenReturn(new Response<>(null, OpCode.Not_Exist_Room));
    }

    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        classroomRepository=new ClassRoomRepositoryMock(dataGenerator);
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);


//        openAnsRoom.addOpenAnswer(new OpenAnswer("open1", "answer", null));
//        openAnsRoomMultiple.addOpenAnswer(new OpenAnswer("open1", "answer", null));
//        openAnsRoomMultiple.addOpenAnswer(new OpenAnswer("open2", "answer", null));
    }

    void setUpAddRoom(){
        setUpMocks();
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Group));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
    }

    @Test
    void testAddRoomValidStudent(){
        setUpAddRoom();
        testAddRoomValidTest(Data.Valid_Student);
        tearDown();
    }

    @Test
    void testAddRoomValidGroup(){
        setUpAddRoom();
        testAddRoomValidTest(Data.Valid_Group);
        tearDown();
    }

    @Test
    void testAddRoomValidClassroom(){
        setUpAddRoom();
        testAddRoomValidTest(Data.Valid_Classroom);
        tearDown();
    }

    protected void testAddRoomValidTest(Data data){
        Response<Boolean> response= roomManger.createRoom(apiKey,dataGenerator.getNewRoomDetails(data));
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
    }

    @Test
    void testAddRoomNullName(){
        setUpAddRoom();
        testAddRoomInValid(Data.NULL_NAME,OpCode.Wrong_Name);
        tearDown();
    }

    @Test
    void testAddRoomEmptyName(){
        setUpAddRoom();
        testAddRoomInValid(Data.EMPTY_NAME,OpCode.Wrong_Name);
        tearDown();
    }


    @Test
    void testAddRoomNullDetails(){
        setUpAddRoom();
        testAddRoomInValid(Data.NULL,OpCode.Null_Error);
        tearDown();
    }

    @Test
    void testAddRoomNegBonus(){
        setUpAddRoom();
        testAddRoomInValid(Data.NEG_AMOUNT,OpCode.Wrong_Bonus);
        tearDown();
    }

    @Test
    void testAddRoomTypeNotMatch(){
        setUpAddRoom();
        testAddRoomInValid(Data.TYPE_NOT_MATCH,OpCode.Type_Not_Match);
        tearDown();
    }

    @Test
    void testAddRoomInvalidClassName(){
        setUpAddRoom();
        testAddRoomInValid(Data.NOT_EXIST_CLASSROOM,OpCode.Not_Exist_Classroom);
        tearDown();
    }

    @Test
    void testAddRoomInvalidGroupName(){
        setUpAddRoom();
        testAddRoomInValid(Data.NOT_EXIST_CLASSGROUP,OpCode.Not_Exist_Group);
        tearDown();
    }

    @Test
    void testAddRoomInvalidStudentName(){
        setUpAddRoom();
        testAddRoomInValid(Data.NOT_EXIST_STUDENT,OpCode.Not_Exist_Student);
        tearDown();
    }

    @Test
    void testAddRoomExceptionTeacherRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,new TeacherCrudRepositoryMockExceptionFindById(),roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDown();
    }

    @Test
    void testAddRoomExceptionRoomTemplateRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,new RoomTemplateCrudRepositoryExceptionFindByIdMock(dataGenerator));
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDown();
    }

    @Test
    void testAddRoomExceptionFindStudentRoomRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomRepositoryExceptionFindParticipantRoomMock(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDown();
    }

    @Test
    void testAddRoomTimeOutExceptionFindStudentRoomRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomRepositoryTimeOutExceptionFindParticipantRoomMock(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.TimeOut);
        tearDown();
    }

    @Test
    void testAddRoomExceptionFindGroupRoomRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomRepositoryExceptionFindParticipantRoomMock(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Group,OpCode.DB_Error);
        tearDown();
    }

    @Test
    void testAddRoomTimeOutExceptionFindGroupRoomRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomRepositoryTimeOutExceptionFindParticipantRoomMock(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Group,OpCode.TimeOut);
        tearDown();
    }

    @Test
    void testAddRoomExceptionFindClassroomRoomRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomRepositoryExceptionFindParticipantRoomMock(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Classroom,OpCode.DB_Error);
        tearDown();
    }

    @Test
    void testAddRoomTimeOutExceptionFindClassroomRoomRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomRepositoryTimeOutExceptionFindParticipantRoomMock(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Classroom,OpCode.TimeOut);
        tearDown();
    }

    @Test
    void testAddRoomExceptionSaveRoomRepo(){
        setUpAddRoom();
        roomManger=new RoomManager(ram,teacherCrudRepository,new RoomCrudRepositoryExceptionSave(dataGenerator),roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDown();
    }

    @Test
    void getClassRoomDataHappyTest(){
        setUpAddRoom();
        Response <ClassRoomData> actual=roomMangerWithMockito.getClassRoomData(apiKey);
        ClassRoomData expected=dataGenerator.getClassroomData(Data.Valid_Classroom);
        assertEquals(actual.getValue(),expected);
        assertEquals(actual.getReason(),OpCode.Success);
        tearDown();
    }

    @Test
    void getClassRoomDataInvalidApiKeyTest(){
        setUpAddRoom();
        apiKey=apiKey+"10";
        testGetClassRoomDataInvalid(OpCode.Wrong_Key);
        tearDown();
    }


    @Test
    void getClassRoomDataNullTeacherTest(){
        setUpAddRoom();
        apiKey=NULL_TEACHER_KEY;
        when(mockTeacherRepo.findTeacherById(WRONG_TEACHER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        testGetClassRoomDataInvalid(OpCode.Not_Exist);
        tearDown();
    }

    @Test
    void getClassRoomFindTeacherByIdThrowsExceptionTest(){
        setUpAddRoom();
        when(mockTeacherRepo.findTeacherById(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM)
                .getAlias()))
                .thenReturn(new Response<>(null
                        ,OpCode.DB_Error));
        testGetClassRoomDataInvalid(OpCode.DB_Error);
        tearDown();
    }

    @Test
    void testResponseStudentSolutionSuccessRejectLastMission() {
        testResponseStudentSolution(apiKey, this.openAnsRoom, false, Data.VALID_OPEN_ANS, OpenAnswerResponseMessages.rejectCloseMessage);
    }

    @Test
    void testResponseStudentSolutionSuccessApproveLastMission() {
        testResponseStudentSolution(apiKey, this.openAnsRoom, true, Data.VALID_OPEN_ANS, OpenAnswerResponseMessages.approveCloseMessage);
    }

    @Test
    void testResponseStudentSolutionSuccessRejectNotLastMission() {
        testResponseStudentSolution(apiKey, this.openAnsRoomMultiple, false, Data.VALID_OPEN_ANS, OpenAnswerResponseMessages.rejectOpenMessage);
    }

    @Test
    void testResponseStudentSolutionSuccessApproveNotLastMission() {
        testResponseStudentSolution(apiKey, this.openAnsRoomMultiple, true, Data.VALID_OPEN_ANS, OpenAnswerResponseMessages.approveOpenMessage);
    }

    @Test
    void testResponseStudentSolutionFailInvalidApiKey(){
        setUpAddRoom();
        Response<String> response = roomMangerWithMockito.responseStudentSolution(apiKey + invalidSuffix, openAnsRoom.getRoomId(), dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), true);
        assertEquals(response.getReason(), OpCode.Wrong_Key);
        assertEquals(null, response.getValue());
        tearDown();
    }

    @Test
    void testResponseStudentSolutionFailInvalidRoomId(){
        setUpAddRoom();
        Response<String> response = roomMangerWithMockito.responseStudentSolution(apiKey, openAnsRoom.getRoomId() + invalidSuffix, dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), true);
        assertEquals(response.getReason(), OpCode.Not_Exist_Room);
        assertEquals(null, response.getValue());
        tearDown();
    }

    @Test
    void testResponseStudentSolutionFailInvalidMissionId() {
        setUpAddRoom();
        Response<String> response = roomMangerWithMockito.responseStudentSolution(apiKey, openAnsRoom.getRoomId(), dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId() + invalidSuffix, true);
        assertEquals(response.getReason(), OpCode.MISSION_NOT_IN_ROOM);
        assertEquals(null, response.getValue());
        tearDown();
    }


    protected void testResponseStudentSolution(String apiKey, Room room, boolean approve, Data missionData, String message) {
        setUpAddRoom();
        Response<String> response = roomMangerWithMockito.responseStudentSolution(apiKey, room.getRoomId(), dataGenerator.getMission(missionData).getMissionId(), approve);
        assertEquals(response.getReason(), OpCode.Success);
        assertEquals(String.format(message, room.getName()), response.getValue());
        tearDown();
    }

    protected void testGetClassRoomDataInvalid(OpCode opcode){
        Response <ClassRoomData> actual=roomMangerWithMockito.getClassRoomData(apiKey);
        assertEquals(actual.getReason(), opcode);
        assertNull(actual.getValue());
    }


    protected void testAddRoomInValid(Data data, OpCode opcode){
        Response<Boolean> response= roomManger.createRoom(apiKey,dataGenerator.getNewRoomDetails(data));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opcode);
    }


    protected void tearDown() {
        roomCrudRepository.deleteAll();
        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();
        missionCrudRepository.deleteAll();
        tearDownMocks();
    }

    protected void tearDownMocks(){
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }
}
