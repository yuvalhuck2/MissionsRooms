package missions.room.ManagerRoomStudentTests;


import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomDetailsData;
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
import missions.room.Domain.Notifications.Notification;
import missions.room.Domain.Ram;
import missions.room.Domain.Room;
import missions.room.Domain.Student;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Repo.ClassGroupRepo;
import missions.room.Repo.ClassroomRepo;
import missions.room.Repo.RoomRepo;
import missions.room.Repo.StudentRepo;
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
import org.springframework.stereotype.Service;

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
    protected RoomCrudRepository mockRoomCrudRepository;

    protected String studentApiKey;

    protected String valid2StudentApiKey;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        studentApiKey = VALID_STUDENT_APIKEY;
        valid2StudentApiKey = VALID2_STUDENT_APIKEY;
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

    protected void initMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        Student student = dataGenerator.getStudent(Data.VALID);
        Student student2 = dataGenerator.getStudent(Data.VALID2);
        Room studentRoom=dataGenerator.getRoom(Data.Valid_Student);
        Room groupRoom=dataGenerator.getRoom(Data.Valid_Group);
        Room classroomRoom = dataGenerator.getRoom(Data.Valid_Classroom);
        Room valid2MissionsStudentRoom =dataGenerator.getRoom(Data.VALID_2MissionStudent);
        Room valid2MissionsGroupRoom =dataGenerator.getRoom(Data.VALID_2Mission_Group);
        Room valid2MissionsClassRoom =dataGenerator.getRoom(Data.VALID_2Mission_Class);
        Room valid2StudentsFromDifferentGroups2Missions=dataGenerator.getRoom(Data.Valid_2Students_From_Different_Groups);

        when(mockRam.getAlias(studentApiKey))
                .thenReturn(student.getAlias());
        when(mockRam.getApiKey(student.getAlias()))
                .thenReturn(studentApiKey);
        when(mockRam.getAlias(INVALID_KEY))
                .thenReturn(null);
        when(mockRam.getAlias(NULL_STUDENT_KEY))
                .thenReturn(WRONG_STUDENT_NAME);
        when(mockRam.getAlias(VALID2_STUDENT_APIKEY))
                .thenReturn(student2.getAlias());
        when(mockRam.getApiKey(student2.getAlias()))
                .thenReturn(VALID2_STUDENT_APIKEY);
        when(mockRam.getRoom(studentRoom.getRoomId()))
                .thenReturn(null)
                .thenReturn(studentRoom);
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

        when(mockStudentRepo.findStudentById(student.getAlias()))
                .thenReturn(new Response<>(student, OpCode.Success));
        when(mockStudentRepo.findStudentById(student2.getAlias()))
                .thenReturn(new Response<>(student2, OpCode.Success));
        when(mockStudentRepo.save(any()))
                .thenReturn(new Response<>(student,OpCode.Success));

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
    }

//    void setUpWatchRoomDetails(){
//        setUpMocks();
//        classroomRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
//        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
//        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
//        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
//        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
//        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Group));
//        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
//    }
//
//
//    void tearDownWatchRoomDetails(){
//        //classroomRepo.delete(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
//        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
//
//        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));
//        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
//        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.VALID));
//        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.Valid_Group));
//        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
//    }

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
    void testAnswerDeterministicStudentFindByIdThrowsException(){
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null,DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.DB_Error);
    }

    @Test
    void testAnswerDeterministicStudentNotFound(){
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null,OpCode.Success));
        testFailAnswerDeterministic(NULL_STUDENT_KEY,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                OpCode.Not_Exist);
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
    void testAnswerDeterministicSaveRoomThrowsException(){
        when(mockRoomRepo.save(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.VALID_2MissionStudent).getRoomId(),
                DB_Error);
    }

    @Test
    void testAnswerDeterministicDeleteRoomThrowsException(){
        when(mockRoomRepo.deleteRoom(any()))
                .thenReturn(new Response<>(null, DB_Error));
        testFailAnswerDeterministic(studentApiKey,
                dataGenerator.getRoom(Data.Valid_Student).getRoomId(),
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
        when(mockStudentRepo.findStudentById(any()))
                .thenReturn(new Response<>(null,OpCode.Success));
        testWatchRoomDataFailCase(NULL_STUDENT_KEY,
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

//    @Test
//    @Transactional
//    void testWatchRoomValid_alltypes(){
//        setUpWatchRoomDetails();
//        testWatchRoomValid_alltypesTest();
//        tearDownWatchRoomDetails();
//    }
//
//    protected void testWatchRoomValid_alltypesTest() {
//        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Group));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Classroom));
//
//
//        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
//        assertEquals(response.getReason(),OpCode.Success);
//        assertEquals(response.getValue().size(), 3);
//
//        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal||
//                response.getValue().get(1).getRoomType()==RoomType.Personal||
//                response.getValue().get(2).getRoomType()==RoomType.Personal);
//        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Group||
//                response.getValue().get(1).getRoomType()==RoomType.Group||
//                response.getValue().get(2).getRoomType()==RoomType.Group);
//        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Class||
//                response.getValue().get(1).getRoomType()==RoomType.Class||
//                response.getValue().get(2).getRoomType()==RoomType.Class);
//
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Group));
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Classroom));
//        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));
//
//    }
//
//    @Test
//    void testWatchRoomInvalidStudent(){
//        setUpWatchRoomDetails();
//        testWatchRoomInvalidStudentTest();
//        tearDownWatchRoomDetails();
//    }
//
//    protected void testWatchRoomInvalidStudentTest() {
//        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("invalidApiKey");
//        assertEquals(response.getReason(),OpCode.Wrong_Key);
//        assertEquals(null,response.getValue());
//    }
//
//
//    @Test
//    @Transactional
//    void testWatchRoomValid_only1room(){
//        setUpWatchRoomDetails();
//        testWatchRoomValid_only1roomTest();
//        tearDownWatchRoomDetails();
//    }
//
//    protected void testWatchRoomValid_only1roomTest() {
//        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
//
//        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
//        assertEquals(response.getReason(),OpCode.Success);
//        assertEquals(response.getValue().size(), 1);
//
//        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);
//
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
//        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));
//
//    }
//
//
//    @Test
//    @Transactional
//    void testWatchRoomValid_2room(){
//        setUpWatchRoomDetails();
//        testWatchRoomValid_2roomTest();
//        tearDownWatchRoomDetails();
//    }
//
//    protected void testWatchRoomValid_2roomTest() {
//        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Group));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Classroom));
//        //roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
///*
//        Room room=dataGenerator.getRoom(Data.Valid_Classroom);
//        //Room room1=roomRepo.findStudentRoomByAlias(dataGenerator.getStudent(Data.VALID).getAlias());
//        Room room2=roomRepo.findClassroomRoomByAlias(dataGenerator.getClassroom(Data.Valid_Classroom).getClassName());
//        String student=dataGenerator.getStudent(Data.VALID).getAlias();
//        Room room3=roomRepo.findGroupRoomByAlias("g2");*/
//        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
//        assertEquals(response.getReason(),OpCode.Success);
//        assertEquals(response.getValue().size(), 2);
//
//        //assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);
//
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Group));
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Classroom));
//        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));
//
//    }
//
//
//    @Test
//    @Transactional
//    void testWatchRoomValid_norooms(){
//        setUpWatchRoomDetails();
//        testWatchRoomValid_noroomsTest();
//        tearDownWatchRoomDetails();
//    }
//
//    protected void testWatchRoomValid_noroomsTest() {
//        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
//        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
//        assertEquals(response.getReason(),OpCode.Success);
//        assertEquals(response.getValue().size(), 0);
//
//        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));
//    }
//
//    @Test
//    @Transactional
//    void testWatchRoomValid_roomInRam(){
//        setUpWatchRoomDetails();
//        testWatchRoomValid_roomInRamTest();
//        tearDownWatchRoomDetails();
//    }
//
//    protected void testWatchRoomValid_roomInRamTest() {
//        ram.addRoom(dataGenerator.getRoom(Data.Valid_Student));
//        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
//        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
//        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
//        assertEquals(response.getReason(),OpCode.Success);
//        assertEquals(response.getValue().size(), 1);
//
//        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);
//
//        ram.deleteRoom(dataGenerator.getRoom(Data.Valid_Student).getRoomId());
//        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
//        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
//        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));
//
//    }

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
