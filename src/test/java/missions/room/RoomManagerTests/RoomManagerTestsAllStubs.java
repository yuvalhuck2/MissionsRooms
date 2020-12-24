package missions.room.RoomManagerTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.RoomCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DomainMocks.MockRam;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.RoomRepository.RoomCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Ram;
import missions.room.Managers.RoomManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    protected DataGenerator dataGenerator;

    protected String apiKey;

    protected Ram ram;

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
    }

    void setUpMocks(){
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        roomCrudRepository=new RoomCrudRepositoryMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        roomManger=new RoomManager(ram,teacherCrudRepository,roomCrudRepository,roomTemplateCrudRepository);
    }

    void setUpAddRoom(){
        setUpMocks();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
    }

    @Test
    void testAddRoomValid(){
        setUpAddRoom();
        testAddRoomValidTest();
        tearDownAddRoom();
    }

    protected void testAddRoomValidTest(){
        Response<Boolean> response= roomManger.createRoom(apiKey,dataGenerator.getNewRoomDetails(Data.Valid_Student));
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
    }

    protected void tearDownAddRoom() {
        teacherCrudRepository.deleteAll();
        missionCrudRepository.deleteAll();
        roomTemplateCrudRepository.deleteAll();
    }
}
