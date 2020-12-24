package missions.room.RoomManagerTests;

import CrudRepositories.*;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
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
import missions.room.Domain.Ram;
import missions.room.Managers.RoomManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.*;

@Service
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

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        apiKey="key";
    }

    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        classroomRepository=new ClassRoomRepositoryMock(dataGenerator);
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
    }

    void setUpAddRoom(){
        setUpMocks();
        classroomRepository.save(dataGenerator.getClassroom(Data.Valid_Classroom));
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Group));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.Valid_Classroom));
    }

    @Test
    void testAddRoomValidStudent(){
        setUpAddRoom();
        testAddRoomValidTest(Data.Valid_Student);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomValidGroup(){
        setUpAddRoom();
        testAddRoomValidTest(Data.Valid_Group);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomValidClassroom(){
        setUpAddRoom();
        testAddRoomValidTest(Data.Valid_Classroom);
        tearDownAddRoom();
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
        tearDownAddRoom();
    }

    @Test
    void testAddRoomEmptyName(){
        setUpAddRoom();
        testAddRoomInValid(Data.EMPTY_NAME,OpCode.Wrong_Name);
        tearDownAddRoom();
    }


    @Test
    void testAddRoomNullDetails(){
        setUpAddRoom();
        testAddRoomInValid(Data.NULL,OpCode.Null_Error);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomNegBonus(){
        setUpAddRoom();
        testAddRoomInValid(Data.NEG_AMOUNT,OpCode.Wrong_Bonus);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomTypeNotMatch(){
        setUpAddRoom();
        testAddRoomInValid(Data.TYPE_NOT_MATCH,OpCode.Type_Not_Match);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomExceptionTeacherRepo(){
        setUpAddRoom();
        teacherCrudRepository=new TeacherCrudRepositoryMockExceptionFindById();
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomExceptionRoomTemplateRepo(){
        setUpAddRoom();
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryExceptionFindByIdMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomExceptionFindStudentRoomRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomRepositoryExceptionFindParticipantRoomMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomTimeOutExceptionFindStudentRoomRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomRepositoryTimeOutExceptionFindParticipantRoomMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.TimeOut);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomExceptionFindGroupRoomRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomRepositoryExceptionFindParticipantRoomMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Group,OpCode.DB_Error);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomTimeOutExceptionFindGroupRoomRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomRepositoryTimeOutExceptionFindParticipantRoomMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Group,OpCode.TimeOut);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomExceptionFindClassroomRoomRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomRepositoryExceptionFindParticipantRoomMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Classroom,OpCode.DB_Error);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomTimeOutExceptionFindClassroomRoomRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomRepositoryTimeOutExceptionFindParticipantRoomMock(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Classroom,OpCode.TimeOut);
        tearDownAddRoom();
    }

    @Test
    void testAddRoomExceptionSaveRoomRepo(){
        setUpAddRoom();
        roomCrudRepository=new RoomCrudRepositoryExceptionSave(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
        testAddRoomInValid(Data.Valid_Student,OpCode.DB_Error);
        tearDownAddRoom();
    }





    protected void testAddRoomInValid(Data data, OpCode opcode){
        Response<Boolean> response= roomManger.createRoom(apiKey,dataGenerator.getNewRoomDetails(data));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opcode);
    }

    protected void tearDownAddRoom() {
        roomCrudRepository.deleteAll();
        teacherCrudRepository.deleteAll();
        classroomRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();
        missionCrudRepository.deleteAll();
    }
}
