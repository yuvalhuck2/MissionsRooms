package missions.room.ManagerRoomStudentTests;


import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomDetailsData;
import DataAPI.RoomType;
import DomainMocks.MissionMock;
import DomainMocks.MockRam;
import RepositoryMocks.ClassGroupRepository.ClassGroupRepositoryMock;
import RepositoryMocks.ClassroomRepository.ClassRoomRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.SchoolUserRepositry.SchoolUserRepositoryMock;
import RepositoryMocks.StudentRepositoryMock.StudentRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import Utils.InterfaceAdapter;
import com.google.gson.GsonBuilder;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.Room;
import missions.room.Domain.RoomTemplate;
import missions.room.Managers.ManagerRoomStudent;
import missions.room.Managers.MissionManager;
import missions.room.Repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Service
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


    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();

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

    void setUpWatchRoomDetails(){
        setUpMocks();
        classroomRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Group));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
    }


    void tearDownWatchRoomDetails(){
        //classroomRepo.delete(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));

        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.VALID));
        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.Valid_Group));
        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
    }


    void setUpAnswerDeterministic(){
        setUpMocks();
        classroomRepo.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Group));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID_2Mission));
    }


    void tearDownAnswerDeterministic(){
        //classroomRepo.delete(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM).getClassroom());
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_CLASSROOM));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic_All_Types));
        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.VALID));
        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.Valid_Group));
        //roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
    }


    @Test
    @Transactional
    void testAnswerDeterministic_valid(){
        setUpAnswerDeterministic();
        testAnswerDeterministic_validTest();
        tearDownAnswerDeterministic();
    }

    protected void testAnswerDeterministic_validTest(){
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));

        Response<Boolean> response=managerRoomStudent.answerDeterministicQuestion("apiKey",dataGenerator.getRoom(Data.Valid_Student).getRoomId(),true);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        assertEquals(studentCrudRepository.findUserForRead(dataGenerator.getStudent(Data.VALID).getAlias()).getPoints(),6);
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
    }

    @Test
    @Transactional
    void testAnswerDeterministic_invalidStudent(){
        setUpAnswerDeterministic();
        testAnswerDeterministic_invalidStudentTest();
        tearDownAnswerDeterministic();
    }

    protected void testAnswerDeterministic_invalidStudentTest(){
        Response<Boolean> response=managerRoomStudent.answerDeterministicQuestion("invalidApiKey",dataGenerator.getRoom(Data.Valid_Student).getRoomId(),true);
        assertEquals(response.getReason(),OpCode.Wrong_Key);
        assertEquals(null,response.getValue());
    }

    @Test
    @Transactional
    void testAnswerDeterministic_wrongAns(){
        setUpAnswerDeterministic();
        testAnswerDeterministic_wrongAnsTest();
        tearDownAnswerDeterministic();
    }

    protected void testAnswerDeterministic_wrongAnsTest(){
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));

        Response<Boolean> response=managerRoomStudent.answerDeterministicQuestion("apiKey",dataGenerator.getRoom(Data.Valid_Student).getRoomId(),false);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        assertEquals(studentCrudRepository.findUserForRead(dataGenerator.getStudent(Data.VALID).getAlias()).getPoints(),0);

        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
    }

    @Test
    void testAnswerDeterministic_wrongRoomId(){
        setUpAnswerDeterministic();
        testAnswerDeterministic_wrongRoomIdTest();
        tearDownAnswerDeterministic();
    }

    protected void testAnswerDeterministic_wrongRoomIdTest(){
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));

        Response<Boolean> response=managerRoomStudent.answerDeterministicQuestion("apiKey","notExistId",false);
        assertEquals(response.getReason(),OpCode.DB_Error);
        assertEquals(null,response.getValue());

        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
    }

    @Test
    @Transactional
    void testAnswerDeterministic_2MissionsRoom(){
        setUpAnswerDeterministic();
        testAnswerDeterministic_2MissionsRoomTest();
        tearDownAnswerDeterministic();
    }

    protected void testAnswerDeterministic_2MissionsRoomTest(){
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        Room r=roomRepo.save(dataGenerator.getRoom(Data.VALID_2Mission));

        Response<Boolean> response=managerRoomStudent.answerDeterministicQuestion("apiKey",dataGenerator.getRoom(Data.VALID_2Mission).getRoomId(),true);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        assertEquals(studentCrudRepository.findUserForRead(dataGenerator.getStudent(Data.VALID).getAlias()).getPoints(),3);
        assertEquals(roomRepo.findRoomForRead(dataGenerator.getRoom(Data.VALID_2Mission).getRoomId()).getCurrentMission(),1);
        assertEquals(roomRepo.findRoomForRead(dataGenerator.getRoom(Data.VALID_2Mission).getRoomId()).getCountCorrectAnswer(),1);

        //TODO without mock i need to check if the points not!!! update,if the room is delete
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        roomRepo.delete(dataGenerator.getRoom(Data.VALID_2Mission));
    }


    @Test
    @Transactional
    void testAnswerDeterministic_2MissionsRoomWrongAns(){
        setUpAnswerDeterministic();
        testAnswerDeterministic_2MissionsRoomWrongAnsTest();
        tearDownAnswerDeterministic();
    }

    protected void testAnswerDeterministic_2MissionsRoomWrongAnsTest(){
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        Room r=roomRepo.save(dataGenerator.getRoom(Data.VALID_2Mission));

        Response<Boolean> response=managerRoomStudent.answerDeterministicQuestion("apiKey",dataGenerator.getRoom(Data.VALID_2Mission).getRoomId(),false);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(true,response.getValue());
        assertEquals(studentCrudRepository.findUserForRead(dataGenerator.getStudent(Data.VALID).getAlias()).getPoints(),0);
        assertEquals(roomRepo.findRoomForRead(dataGenerator.getRoom(Data.VALID_2Mission).getRoomId()).getCurrentMission(),1);
        assertEquals(roomRepo.findRoomForRead(dataGenerator.getRoom(Data.VALID_2Mission).getRoomId()).getCountCorrectAnswer(),0);

        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        roomRepo.delete(dataGenerator.getRoom(Data.VALID_2Mission));
    }


    @Test
    @Transactional
    void testWatchRoomValid_alltypes(){
        setUpWatchRoomDetails();
        testWatchRoomValid_alltypesTest();
        tearDownWatchRoomDetails();
    }

    protected void testWatchRoomValid_alltypesTest() {
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Group));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Classroom));


        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 3);

        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal||
                response.getValue().get(1).getRoomType()==RoomType.Personal||
                response.getValue().get(2).getRoomType()==RoomType.Personal);
        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Group||
                response.getValue().get(1).getRoomType()==RoomType.Group||
                response.getValue().get(2).getRoomType()==RoomType.Group);
        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Class||
                response.getValue().get(1).getRoomType()==RoomType.Class||
                response.getValue().get(2).getRoomType()==RoomType.Class);

        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Group));
        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Classroom));
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));

    }

    @Test
    void testWatchRoomInvalidStudent(){
        setUpWatchRoomDetails();
        testWatchRoomInvalidStudentTest();
        tearDownWatchRoomDetails();
    }

    protected void testWatchRoomInvalidStudentTest() {
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("invalidApiKey");
        assertEquals(response.getReason(),OpCode.Wrong_Key);
        assertEquals(null,response.getValue());
    }


    @Test
    @Transactional
    void testWatchRoomValid_only1room(){
        setUpWatchRoomDetails();
        testWatchRoomValid_only1roomTest();
        tearDownWatchRoomDetails();
    }

    protected void testWatchRoomValid_only1roomTest() {
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));

        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 1);

        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);

        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));

    }


    @Test
    @Transactional
    void testWatchRoomValid_2room(){
        setUpWatchRoomDetails();
        testWatchRoomValid_2roomTest();
        tearDownWatchRoomDetails();
    }

    protected void testWatchRoomValid_2roomTest() {
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Group));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Classroom));
        //roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
/*
        Room room=dataGenerator.getRoom(Data.Valid_Classroom);
        //Room room1=roomRepo.findStudentRoomByAlias(dataGenerator.getStudent(Data.VALID).getAlias());
        Room room2=roomRepo.findClassroomRoomByAlias(dataGenerator.getClassroom(Data.Valid_Classroom).getClassName());
        String student=dataGenerator.getStudent(Data.VALID).getAlias();
        Room room3=roomRepo.findGroupRoomByAlias("g2");*/
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 2);

        //assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);

        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Group));
        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Classroom));
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));

    }


    @Test
    @Transactional
    void testWatchRoomValid_norooms(){
        setUpWatchRoomDetails();
        testWatchRoomValid_noroomsTest();
        tearDownWatchRoomDetails();
    }

    protected void testWatchRoomValid_noroomsTest() {
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 0);

        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));
    }

    @Test
    @Transactional
    void testWatchRoomValid_roomInRam(){
        setUpWatchRoomDetails();
        testWatchRoomValid_roomInRamTest();
        tearDownWatchRoomDetails();
    }

    protected void testWatchRoomValid_roomInRamTest() {
        ram.addRoom(dataGenerator.getRoom(Data.Valid_Student).getRoomId(),dataGenerator.getRoom(Data.Valid_Student));
        roomRepo.save(dataGenerator.getRoom(Data.Valid_Student));
        studentCrudRepository.save(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        Response<List<RoomDetailsData>> response=managerRoomStudent.watchRoomDetails("apiKey");
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(), 1);

        assertTrue(response.getValue().get(0).getRoomType()==RoomType.Personal);

        ram.deleteRoom(dataGenerator.getRoom(Data.Valid_Student).getRoomId());
        roomRepo.delete(dataGenerator.getRoom(Data.Valid_Student));
        studentCrudRepository.delete(dataGenerator.getStudent(Data.VALID));
        //classroomRepo.delete(dataGenerator.getClassroom(Data.Valid_Classroom));

    }


}
