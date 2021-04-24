package missions.room.ManagerRoomStudentTests;


import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import DataAPI.*;
import DomainMocks.MockRam;
import DomainMocks.PublisherMock;
import RepositoryMocks.ClassGroupRepository.ClassGroupRepositoryMock;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Communications.Publisher.Publisher;
import missions.room.Domain.Notifications.NonPersistenceNotification;
import missions.room.Domain.Notifications.Notification;
import missions.room.Domain.OpenAnswer;
import missions.room.Domain.Ram;
import missions.room.Domain.Rooms.Room;
import missions.room.Domain.Users.Student;
import missions.room.Domain.Users.BaseUser;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Repo.*;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import java.lang.reflect.Field;

import static Data.DataConstants.*;
import static DataAPI.OpCode.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Service
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ManagerRoomStudentAllStubs {

    @Autowired
    protected ManagerRoomStudent managerRoomStudent;

    @Autowired
    protected StudentCrudRepository studentCrudRepository;

    @Autowired
    protected RoomCrudRepository roomRepo;

    @Autowired
    protected OpenAnswerRepo openAnswerRepo;

    @Autowired
    protected ClassroomRepository classroomRepo;

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected GroupRepository groupRepository;

    protected DataGenerator dataGenerator;

    protected Ram ram;

    /**
     * mockito tests mocks
     */
    @InjectMocks
    protected ManagerRoomStudent managerRoomStudentWithMock;

    @Mock
    protected Ram mockRam;

    @Mock
    protected StudentRepo mockStudentRepo;

    @Mock
    protected StudentCrudRepository mockStudentCrudRepository;

    @Mock
    protected ClassroomRepo mockClassroomRepo;

    @Mock
    protected ClassroomRepository mockClassroomCrudRepository;

    @Mock
    protected ClassGroupRepo mockClassGroupRepo;

    @Mock
    protected GroupRepository mockGroupRepository;

    protected Publisher mockPublisher;

    @Mock
    protected RoomRepo mockRoomRepo;

    @Mock
    protected OpenAnswerRepo mockOpenAnswerRepo;

    @Mock
    protected RoomCrudRepository mockRoomCrudRepository;

    protected String studentApiKey;

    protected String valid2StudentApiKey;

    protected String thirdStudentKey;

    protected String validRoomId;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        valid2StudentApiKey = VALID2_STUDENT_APIKEY;
        thirdStudentKey=THIRD_VALID_STUDENT_APIKEY;
        initMocks();
        mockPublisher=new PublisherMock();
        try {
            Field publisher = ManagerRoomStudent.class.getDeclaredField("publisher");
            publisher.setAccessible(true);
            publisher.set(managerRoomStudentWithMock,mockPublisher);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
    }

    protected void initStoryRoom(){
        Room room =dataGenerator.getRoom(Data.VALID_STORY);
        Student student = dataGenerator.getStudent(Data.VALID);
        Student student2 = dataGenerator.getStudent(Data.VALID2);
        initMockRoom(Data.VALID_STORY);
        room.connect(student.getAlias());
        room.connect(student2.getAlias());
        validRoomId=room.getRoomId();
        when(mockRam.isRoomExist(room.getRoomId()))
                .thenReturn(true);
    }

    protected void initFinishStoryRoom() {
        initStoryRoom();
        Room room =dataGenerator.getRoom(Data.VALID_STORY);
        managerRoomStudentWithMock.answerStoryMission(studentApiKey,room.getRoomId(),SENTENCE);
    }

    protected void initMockRoom(Data data){
        Room room =dataGenerator.getRoom(data);
        when(mockRam.getRoom(room.getRoomId()))
                .thenReturn(null)
                .thenReturn(room);
        when(mockRoomRepo.findRoomById(room.getRoomId()))
                .thenReturn(new Response<>(room, Success));
    }

    protected void initMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        Student student = dataGenerator.getStudent(Data.VALID);
        Student student2 = dataGenerator.getStudent(Data.VALID2);
        BaseUser baseUser =dataGenerator.getUser(Data.VALID_STUDENT);
        Room studentRoom=dataGenerator.getRoom(Data.Valid_Student);
        studentRoom.connect(student.getAlias());
        Room groupRoom=dataGenerator.getRoom(Data.Valid_Group);
        groupRoom.connect(student.getAlias());
        Room classroomRoom = dataGenerator.getRoom(Data.Valid_Classroom);
        classroomRoom.connect(student.getAlias());
        Room valid2MissionsStudentRoom =dataGenerator.getRoom(Data.VALID_2MissionStudent);
        valid2MissionsStudentRoom.connect(student.getAlias());
        Room valid2MissionsGroupRoom =dataGenerator.getRoom(Data.VALID_2Mission_Group);
        valid2MissionsGroupRoom.connect(student.getAlias());
        Room valid2MissionsClassRoom =dataGenerator.getRoom(Data.VALID_2Mission_Class);
        valid2MissionsClassRoom.connect(student.getAlias());
        Room valid2StudentsFromDifferentGroups2Missions=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        valid2StudentsFromDifferentGroups2Missions.connect(student.getAlias());
        valid2StudentsFromDifferentGroups2Missions.connect(student2.getAlias());
        Room openAnsRoom = dataGenerator.getRoom((Data.VALID_OPEN_ANS));

        when(mockRam.getAlias(thirdStudentKey))
                .thenReturn(baseUser.getAlias());
        when(mockRam.getAlias(studentApiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getApiKey(student.getAlias()))
                .thenReturn(studentApiKey);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_USER_KEY))
                .thenReturn(WRONG_USER_NAME);
        when(mockRam.getAlias(VALID2_STUDENT_APIKEY))
                .thenReturn(student2.getAlias());
        when(mockRam.getApiKey(student2.getAlias()))
                .thenReturn(VALID2_STUDENT_APIKEY);
        when(mockRam.getRoom(studentRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(studentRoom);
        when(mockRam.getRoom(openAnsRoom.getRoomId()))
                .thenReturn(openAnsRoom);
        when(mockRam.getRoom(groupRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(groupRoom);
        when(mockRam.getRoom(classroomRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(classroomRoom);
        when(mockRam.getRoom(valid2MissionsStudentRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(valid2MissionsStudentRoom);
        when(mockRam.getRoom(valid2MissionsGroupRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(valid2MissionsGroupRoom);
        when(mockRam.getRoom(valid2MissionsClassRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(valid2MissionsClassRoom);
        when(mockRam.getRoom(valid2StudentsFromDifferentGroups2Missions.getRoomId()))
                .thenReturn(null)
                .thenReturn(valid2StudentsFromDifferentGroups2Missions);
        when(mockRam.connectToRoom(valid2MissionsStudentRoom.getRoomId(),student.getAlias()))
                .thenReturn(IN_CHARGE);
        when(mockRam.connectToRoom(valid2StudentsFromDifferentGroups2Missions.getRoomId(),student.getAlias()))
                .thenReturn(IN_CHARGE);
        when(mockRam.connectToRoom(valid2StudentsFromDifferentGroups2Missions.getRoomId(),student2.getAlias()))
                .thenReturn(NOT_IN_CHARGE);
        when(mockRam.disconnectFromRoom(valid2StudentsFromDifferentGroups2Missions.getRoomId(),student.getAlias()))
                .thenReturn(student2.getAlias());
        when(mockRam.connectToRoom(openAnsRoom.getRoomId(), student.getAlias()))
                .thenReturn(IN_CHARGE);

        when(mockRam.getMissionManager(openAnsRoom.getRoomId()))
                .thenReturn(student.getAlias());
        when(mockRam.getAlias(INVALID_KEY_OPEN_ANS))
                .thenReturn("alias");

        when(mockStudentRepo.findStudentById(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockStudentRepo.findStudentById(baseUser.getAlias()))
                .thenReturn(new Response<>((Student) baseUser, OpCode.Success));
        when(mockStudentRepo.findStudentById(student2.getAlias()))
                .thenReturn(new Response<>(student2, OpCode.Success));
        when(mockStudentRepo.findStudentById(WRONG_USER_NAME))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockStudentRepo.save(any()))
                .thenReturn(new Response<>(student,OpCode.Success));

        when(mockOpenAnswerRepo.saveOpenAnswer(any(), any()))
                .thenReturn(new Response<>(true, Success));

        when(mockClassroomRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockClassGroupRepo.save(any()))
                .thenReturn(new Response<>(null,OpCode.Success));


        when(mockRoomRepo.findRoomById(studentRoom.getRoomId()))
                .thenReturn(new Response<>(studentRoom, Success));
        when(mockRoomRepo.findRoomById(groupRoom.getRoomId()))
                .thenReturn(new Response<>(groupRoom, Success));
        when(mockRoomRepo.findRoomById(classroomRoom.getRoomId()))
                .thenReturn(new Response<>(classroomRoom, Success));
        when(mockRoomRepo.findRoomById(valid2MissionsStudentRoom.getRoomId()))
                .thenReturn(new Response<>(valid2MissionsStudentRoom, Success));
        when(mockRoomRepo.findRoomById(valid2MissionsGroupRoom.getRoomId()))
                .thenReturn(new Response<>(valid2MissionsGroupRoom, Success));
        when(mockRoomRepo.findRoomById(valid2MissionsClassRoom.getRoomId()))
                .thenReturn(new Response<>(valid2MissionsClassRoom, Success));
        when(mockRoomRepo.findRoomById(valid2StudentsFromDifferentGroups2Missions.getRoomId()))
                .thenReturn(new Response<>(valid2StudentsFromDifferentGroups2Missions, Success));
        when(mockRoomRepo.deleteRoom(any()))
                .thenReturn(new Response<>(true,OpCode.Success));
        when(mockRoomRepo.findRoomById(WRONG_ROOM_ID))
                .thenReturn(new Response<>(null,OpCode.Success));
        when(mockRoomRepo.save(any()))
                .thenReturn(new Response<>(valid2MissionsStudentRoom,OpCode.Success));
    }

    void setUpMocks(){
        roomRepo=new RoomCrudRepositoryMock(dataGenerator);
        classroomRepo=new ClassRoomRepositoryMock(dataGenerator);
        studentCrudRepository=new StudentRepositoryMock(dataGenerator);
        groupRepository=new ClassGroupRepositoryMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        managerRoomStudent=new ManagerRoomStudent(ram,studentCrudRepository,roomRepo,classroomRepo,groupRepository);

        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        ram.addApi("apiKey",dataGenerator.getStudent(Data.VALID).getAlias());
    }

    @Test
    public void testAnswerDeterministicHappyTest(){
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,dataGenerator.getRoom(Data.Valid_Student).getRoomId(),true);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(),Finish_Missions_In_Room);
        assertNull(notification.getValue());
    }

    @Test
    void testAnswerDeterministicInvalidApiKey(){
        testFailAnswerDeterministic(INVALID_KEY,
                dataGenerator.getRoom(Data.Valid_Student)
                .getRoomId(),
                OpCode.Wrong_Key
                );
    }

    @Test
    void testAnswerDeterministic_wrongAnsTest(){
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Student)
                        .getRoomId(),
                false);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
    }

    @Test
    void testAnswerDeterministic_wrongRoomIdTest(){
        testFailAnswerDeterministic(studentApiKey,
                WRONG_ROOM_ID,
                OpCode.Not_Exist_Room);
    }

    @Test
    void testAnswerDeterministic_2MissionsRoomStudentTest(){
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),true);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(),OpCode.Update_Room);
        RoomDetailsData detailsData= (RoomDetailsData) notification.getValue();
        checkEqualsWithRoomData(Data.VALID_2MissionStudent,detailsData);
    }

    @Test
    void testAnswerDeterministic_2MissionsRoomGroupTest(){
        String roomId=dataGenerator.getRoom(Data.VALID_2Mission_Group).getRoomId();
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,roomId,true);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(),OpCode.Update_Room);
        RoomDetailsData detailsData= (RoomDetailsData) notification.getValue();
        checkEqualsWithRoomData(Data.VALID_2Mission_Group,detailsData);
        Notification inChargeNotification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(1);
        assertEquals(inChargeNotification.getReason(),IN_CHARGE);
        assertEquals(roomId,inChargeNotification.getValue());
    }

    @Test
    void testAnswerDeterministic_2MissionsRoomClassTest(){
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,dataGenerator.getRoom(Data.VALID_2Mission_Class).getRoomId(),true);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(),OpCode.Update_Room);
        RoomDetailsData detailsData= (RoomDetailsData) notification.getValue();
        checkEqualsWithRoomData(Data.VALID_2Mission_Class,detailsData);
    }

    @Test
    void testAnswerDeterministic_2MissionsRoomWrongAnsTest(){
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),false);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(),OpCode.Update_Room);
        RoomDetailsData detailsData= (RoomDetailsData) notification.getValue();
        checkEqualsWithRoomData(Data.VALID_2MissionStudent,detailsData);
    }

    @Test
    void testAnswerDeterministicHasUnapprovedOpenQuestionSolution(){
        Room room =dataGenerator.getRoom(Data.Valid_Student);
        room.addOpenAnswer(new OpenAnswer());
        Response<Boolean> response = managerRoomStudentWithMock.answerDeterministicQuestion(studentApiKey,room.getRoomId(),false);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(), Has_Unapproved_Solutions);
        assertNull(notification.getValue());
    }

    @Test
    void testAnswerDeterministicStudentFindByIdThrowsException(){
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null,DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.DB_Error);
    }

    @Test
    void testAnswerDeterministicStudentNotFound(){
        testFailAnswerDeterministic(NULL_USER_KEY,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.Not_Exist);
    }

    @Test
    void testAnswerDeterministicStudentNotInCharge(){
        testFailAnswerDeterministic(valid2StudentApiKey,
                dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups).getRoomId(),
                NOT_IN_CHARGE);
    }

    @Test
    void testAnswerDeterministicFindRoomThrowsException(){
        when(mockRoomRepo.findRoomById(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                DB_Error);
    }

    @Test
    void testAnswerDeterministicSaveStudentThrowsException(){
        when(mockStudentRepo.save(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Student).getRoomId(),
                DB_Error);
    }

    @Test
    void testAnswerDeterministicSaveGroupThrowsException(){
        when(mockClassGroupRepo.save(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Group).getRoomId(),
                DB_Error);
    }

    @Test
    void testAnswerDeterministicSaveClassThrowsException(){
        when(mockClassroomRepo.save(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Classroom).getRoomId(),
                DB_Error);
    }

    protected void checkEqualsWithRoomData(Data data,RoomDetailsData detailsData) {
        Room r=dataGenerator.getRoom(data);
        assertEquals(detailsData,r.getData());
    }

    protected void testFailAnswerDeterministic(String apiKey, String roomId, OpCode opCode) {
        Response<Boolean> response=managerRoomStudentWithMock.answerDeterministicQuestion(apiKey, roomId,true);
        assertEquals(response.getReason(),opCode);
        assertEquals(null,response.getValue());
        assertTrue(((PublisherMock)mockPublisher).isEmpty());
    }


    @Test
    void testWatchRoomDataHappyTest(){
        Room room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        try {
            Field incharge = Room.class.getDeclaredField("missionIncharge");
            incharge.setAccessible(true);
            incharge.set(room,null);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }

        Response<RoomDetailsData> roomDetailsDataResponse=managerRoomStudentWithMock.watchRoomData(studentApiKey,
                room.getRoomId());
        assertEquals(roomDetailsDataResponse.getReason(), IN_CHARGE);
        assertEquals(roomDetailsDataResponse.getValue(),room.getData());
    }

    @Test
    void testWatchRoomDataNotInChargeStudent(){
        Room room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        managerRoomStudentWithMock.watchRoomData(studentApiKey,
                room.getRoomId());
        Response<RoomDetailsData> roomDetailsDataResponse=managerRoomStudentWithMock.watchRoomData(valid2StudentApiKey,
                room.getRoomId());
        assertEquals(roomDetailsDataResponse.getReason(), NOT_IN_CHARGE);
        assertEquals(roomDetailsDataResponse.getValue(),room.getData());
    }

    @Test
    void testWatchRoomDataWrongRoomId(){
        testWatchRoomDataFailCase(studentApiKey,
                WRONG_ROOM_ID,
                OpCode.Not_Exist_Room);
    }

    @Test
    void testWatchRoomDataInvalidApiKey(){
        testFailAnswerDeterministic(INVALID_KEY,
                dataGenerator.getRoom(Data.Valid_Student)
                        .getRoomId(),
                OpCode.Wrong_Key
        );
    }

    @Test
    void testWatchRoomDataStudentFindByIdThrowsException(){
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null,DB_Error));
        testWatchRoomDataFailCase(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.DB_Error);
    }

    @Test
    void testWatchRoomDataStudentNotFound(){
        testWatchRoomDataFailCase(NULL_USER_KEY,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.Not_Exist);
    }

    @Test
    void testWatchRoomDataFindRoomByIdThrowsException(){
        when(mockRoomRepo.findRoomById(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testWatchRoomDataFailCase(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                DB_Error);
    }

    @Test
    void testWatchRoomDataNotBelongToRoomStudent(){
        testWatchRoomDataFailCase(VALID2_STUDENT_APIKEY,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                NOT_BELONGS_TO_ROOM);
    }

    protected void testWatchRoomDataFailCase(String apiKey, String roomId, OpCode expected) {
        Response<RoomDetailsData> roomDetailsDataResponse=managerRoomStudentWithMock.watchRoomData(apiKey,
                roomId);
        assertEquals(roomDetailsDataResponse.getReason(), expected);
        assertNull(roomDetailsDataResponse.getValue());
    }

    @Test
    void disconnectFromRoomHappyCase(){
        Room room=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);
        room.connect(dataGenerator.getStudent(Data.VALID).getAlias());
        room.connect(dataGenerator.getStudent(Data.VALID2).getAlias());
        managerRoomStudentWithMock.disconnectFromRoom(studentApiKey,
                room.getRoomId());
        Notification notification=((PublisherMock)mockPublisher).getNotifications(valid2StudentApiKey).get(0);
        assertEquals(notification.getReason(),OpCode.IN_CHARGE);
        assertEquals(notification.getValue(),room.getRoomId());
        assertNull(((PublisherMock)mockPublisher).getNotifications(studentApiKey));
    }

    @Test
    @Transactional
    void testWatchRoomValid_alltypes(){
        setUpMocks();
        testWatchRoomValid_alltypesTest();
    }

    protected void testWatchRoomValid_alltypesTest() {
        roomRepo.deleteAll();
        teacherCrudRepository.deleteAll();
        classroomRepo.deleteAll();
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));

        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Group));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Classroom));

        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 3);

        assertTrue(response.getValue().get(0).getRoomType()== RoomType.Personal||
                response.getValue().get(1).getRoomType()==RoomType.Personal||
                response.getValue().get(2).getRoomType()==RoomType.Personal);
        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Group||
                response.getValue().get(1).getRoomType()==RoomType.Group||
                response.getValue().get(2).getRoomType()==RoomType.Group);
        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Class||
                response.getValue().get(1).getRoomType()==RoomType.Class||
                response.getValue().get(2).getRoomType()==RoomType.Class);
    }

    @Test
    void testWatchRoomInvalidStudent(){
        setUpMocks();
        testWatchRoomInvalidStudentTest();
    }

    protected void testWatchRoomInvalidStudentTest() {
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("invalidApiKey");
        assertEquals(response.getReason(),OpCode.Wrong_Key);
        assertEquals(null,response.getValue());
    }


    @Test
    @Transactional
    void testWatchRoomValid_only1room(){
        setUpMocks();
        testWatchRoomValid_only1roomTest();
    }

    protected void testWatchRoomValid_only1roomTest() {
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        roomRepo.deleteAll();
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));

        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 1);

        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);

        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));

    }


    @Test
    @Transactional
    void testWatchRoomValid_2room(){
        setUpMocks();
        testWatchRoomValid_2roomTest();
    }

    protected void testWatchRoomValid_2roomTest() {
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        roomRepo.deleteAll();
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Group));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));

        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 2);

    }


    @Test
    @Transactional
    void testWatchRoomValid_noRooms(){
        setUpMocks();
        testWatchRoomValid_noRoomsTest();
    }

    protected void testWatchRoomValid_noRoomsTest() {
        roomRepo.deleteAll();
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 0);

    }

    @Test
    @Transactional
    void testWatchRoomValid_roomInRam(){
        setUpMocks();
        testWatchRoomValid_roomInRamTest();
    }

    protected void testWatchRoomValid_roomInRamTest() {
        roomRepo.deleteAll();
        ram.addRoom(dataGenerator.getRoom(Data.Valid_Student));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 1);

        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);

        ram.deleteRoom(dataGenerator.getRoom(Data.Valid_Student).getRoomId());
        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));

    }

    @Test
    void testAnswerStoryHappyCase(){
        initStoryRoom();
        Response<Boolean> response = managerRoomStudentWithMock.answerStoryMission(studentApiKey,
                validRoomId,
                SENTENCE);
        assertTrue(response.getValue());
        assertEquals(response.getReason(), Success);

        checkNotification(valid2StudentApiKey,
                STORY_IN_CHARGE,
                SENTENCE + "\n",
                0);

        response = managerRoomStudentWithMock.answerStoryMission(valid2StudentApiKey,
                validRoomId,
                SENTENCE2);
        assertTrue(response.getValue());
        assertEquals(response.getReason(), Success);

        checkNotification(studentApiKey,
                STORY_FINISH,
                SENTENCE + "\n" + SENTENCE2 + "\n",
                0);

        checkNotification(valid2StudentApiKey,
                STORY_FINISH,
                SENTENCE + "\n" + SENTENCE2 + "\n",
                1);
    }

    private void checkNotification(String apiKey,OpCode opCode,String content,int index){
        NonPersistenceNotification<String> notification= (NonPersistenceNotification<String>) ((PublisherMock)mockPublisher)
                .getNotifications(apiKey).get(index);
        assertEquals(notification.getAdditionalData(),content);
        assertEquals(notification.getReason(), opCode);
        assertEquals(notification.getValue(), validRoomId);
    }

    @Test
    void testAnswerStoryWrongKey(){
        initStoryRoom();
        studentApiKey=INVALID_KEY;
        testInvalidAnswerStory(Wrong_Key);
    }

    @Test
    void testAnswerStoryNotExistStudent(){
        initStoryRoom();
        studentApiKey=NULL_USER_KEY;
        testInvalidAnswerStory(Not_Exist);
    }

    @Test
    void testAnswerStoryFindStudentThrowsException(){
        initStoryRoom();
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testInvalidAnswerStory(DB_Error);
    }

    @Test
    void testAnswerStoryNotExistRoom(){
        initStoryRoom();
        validRoomId=WRONG_ROOM_ID;
        testInvalidAnswerStory(Not_Exist_Room);
    }

    @Test
    void testAnswerStoryFindRoomByIdThrowsException(){
        initStoryRoom();
        when(mockRoomRepo.findRoomById(any()))
                .thenReturn(new Response<>(null,DB_Error));
        testInvalidAnswerStory(DB_Error);
    }

    @Test
    void testAnswerStoryNullSentence(){
        initStoryRoom();
        Response<Boolean> response = managerRoomStudentWithMock.answerStoryMission(studentApiKey,
                validRoomId,
                null);
        assertNull(response.getValue());
        assertEquals(response.getReason(), Wrong_Sentence);
        assertTrue(((PublisherMock)mockPublisher).getAllNotifications().isEmpty());
    }

    @Test
    void testAnswerStoryEmptySentence(){
        initStoryRoom();
        Response<Boolean> response = managerRoomStudentWithMock.answerStoryMission(studentApiKey,
                validRoomId,
                "");
        assertNull(response.getValue());
        assertEquals(response.getReason(), Wrong_Sentence);
        assertTrue(((PublisherMock)mockPublisher).getAllNotifications().isEmpty());
    }

    @Test
    void testAnswerStoryNotBelongToRoom(){
        initStoryRoom();
        studentApiKey=thirdStudentKey;
        testInvalidAnswerStory(NOT_BELONGS_TO_ROOM);
    }

    @Test
    void testAnswerStoryNotStoryMission(){
        initStoryRoom();
        validRoomId=dataGenerator.getRoom(Data.Valid_Classroom).getRoomId();
        testInvalidAnswerStory(Not_Story);
    }

    @Test
    void testAnswerStoryNotEnoughConnected(){
        initStoryRoom();
        Student student = dataGenerator.getStudent(Data.VALID);
        Student student2 = dataGenerator.getStudent(Data.VALID2);
        Room room= dataGenerator.getRoom(Data.VALID_STORY);
        room.disconnect(student.getAlias());
        room.disconnect(student2.getAlias());
        testInvalidAnswerStory(Not_Enough_Connected);
    }

    protected void testInvalidAnswerStory(OpCode opCode) {
        Response<Boolean> response = managerRoomStudentWithMock.answerStoryMission(studentApiKey,
                validRoomId,
                SENTENCE);
        assertNull(response.getValue());
        assertEquals(response.getReason(), opCode);
        assertTrue(((PublisherMock)mockPublisher).getAllNotifications().isEmpty());
    }

    @Test
    void testFinishStoryMissionHappyCase(){
        initFinishStoryRoom();
        Room room=dataGenerator.getRoom(Data.VALID_STORY);
        RoomDetailsData roomDetailsData=room.getData();
        roomDetailsData.setCurrentMission(dataGenerator.getMission(Data.VALID_STORY2).getData());

        Response<Boolean> response = managerRoomStudentWithMock.finishStoryMission(studentApiKey,validRoomId);
        assertTrue(response.getValue());
        assertEquals(response.getReason(), Success);

        NonPersistenceNotification<String> notification= (NonPersistenceNotification<String>) ((PublisherMock)mockPublisher)
                .getNotifications(studentApiKey).get(0);
        assertEquals(notification.getReason(), Update_Room);
        assertEquals(notification.getValue(), roomDetailsData);

        notification= (NonPersistenceNotification<String>) ((PublisherMock)mockPublisher)
                .getNotifications(valid2StudentApiKey).get(1);
        assertEquals(notification.getReason(), Update_Room);
        assertEquals(notification.getValue(), roomDetailsData);

        try {
            notification = (NonPersistenceNotification<String>) ((PublisherMock) mockPublisher)
                    .getNotifications(valid2StudentApiKey).get(2);
            assertEquals(notification.getReason(), IN_CHARGE);
            assertEquals(notification.getValue(), room.getRoomId());
        }
        catch(Exception e){
            notification = (NonPersistenceNotification<String>) ((PublisherMock) mockPublisher)
                    .getNotifications(studentApiKey).get(1);
            assertEquals(notification.getReason(), IN_CHARGE);
            assertEquals(notification.getValue(), room.getRoomId());
        }
    }


    @Test
    void testFinishStoryMissionWrongKey(){
        initStoryRoom();
        studentApiKey=INVALID_KEY;
        testInvalidFinishStoryMission(Wrong_Key);
    }


    @Test
    void testFinishStoryMissionNotExistStudent(){
        initStoryRoom();
        studentApiKey=NULL_USER_KEY;
        testInvalidFinishStoryMission(Not_Exist);
    }

    @Test
    void testAFinishStoryMissionFindStudentThrowsException(){
        initStoryRoom();
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testInvalidFinishStoryMission(DB_Error);
    }

    @Test
    void testFinishStoryMissionNotExistRoom(){
        initStoryRoom();
        validRoomId=WRONG_ROOM_ID;
        testInvalidFinishStoryMission(Not_Exist_Room);
    }

    @Test
    void testFinishStoryMissionFindRoomByIdThrowsException(){
        initStoryRoom();
        when(mockRoomRepo.findRoomById(any()))
                .thenReturn(new Response<>(null,DB_Error));
        testInvalidFinishStoryMission(DB_Error);
    }

    @Test
    void testFinishStoryMissionNotBelongToRoom(){
        initStoryRoom();
        studentApiKey=thirdStudentKey;
        testInvalidFinishStoryMission(NOT_BELONGS_TO_ROOM);
    }

    @Test
    void testFinishStoryMissionNotStoryMission(){
        initStoryRoom();
        validRoomId=dataGenerator.getRoom(Data.Valid_Classroom).getRoomId();
        testInvalidFinishStoryMission(Not_Story);
    }

    protected void testInvalidFinishStoryMission(OpCode opCode) {
        Response<Boolean> response = managerRoomStudentWithMock.finishStoryMission(studentApiKey,validRoomId);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
        assertTrue(((PublisherMock)mockPublisher).getAllNotifications().isEmpty());
    }

    @Test void testAnswerOpenQuestionWithOutFileSuccess(){
        SolutionData solutionData = new SolutionData(dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), "ans");
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(studentApiKey, solutionData, null);
        assertEquals(res.getReason(),OpCode.Success);
        assertTrue(res.getValue());
    }

    @Test void testAnswerOpenQuestionWithFileSuccess(){
        byte[] fileContent = "mock file".getBytes();
        MultipartFile mockFile = new MockMultipartFile("file.txt", fileContent);
        SolutionData solutionData = new SolutionData(dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), "ans");
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(studentApiKey, solutionData, mockFile);
        assertEquals(res.getReason(),OpCode.Success);
        assertTrue(res.getValue());
    }

    @Test
    void testAnswerOpenQuestionFailInvalidApiKey(){
        SolutionData solutionData = new SolutionData(dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), "ans");
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(INVALID_KEY_OPEN_ANS, solutionData, null);
        assertEquals(res.getReason(),OpCode.STUDENT_NOT_IN_CHARGE);
        assertFalse(res.getValue());
    }

    @Test
    void testAnswerOpenQuestionFailInvalidMissionId(){
        SolutionData solutionData = new SolutionData("", dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), "ans");
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(INVALID_KEY_OPEN_ANS, solutionData, null);
        assertEquals(res.getReason(),OpCode.STUDENT_NOT_IN_CHARGE);
        assertFalse(res.getValue());
    }

    @Test
    void testAnswerOpenQuestionFailInvalidRoomId(){
        SolutionData solutionData = new SolutionData(dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), WRONG_ROOM_ID, "ans");
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(studentApiKey, solutionData, null);
        assertEquals(res.getReason(), INVALID_ROOM_ID);
        assertFalse(res.getValue());
    }

    @Test
    void testAnswerOpenQuestionFailInvalidNoAnswer() {
        SolutionData solutionData = new SolutionData(dataGenerator.getMission(Data.VALID_OPEN_ANS).getMissionId(), dataGenerator.getRoom(Data.VALID_OPEN_ANS).getRoomId(), null);
        Response<Boolean> res = managerRoomStudentWithMock.answerOpenQuestionMission(studentApiKey, solutionData, null);
        assertEquals(res.getReason(), INVALID_ANSWER);
        assertFalse(res.getValue());
    }


    @AfterEach
    void tearDown() {
        tearDownMocks();
    }

    protected void tearDownMocks(){
        ((PublisherMock)mockPublisher).clear();
        try {
            closeable.close();
        } catch (Exception e) {
            fail("close mocks when don't need to");
        }
    }

}
