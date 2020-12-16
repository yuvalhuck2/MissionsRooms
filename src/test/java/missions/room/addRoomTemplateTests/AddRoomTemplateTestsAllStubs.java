package missions.room.addRoomTemplateTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DomainMocks.MockRam;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMockExeptionFindById;
import RepositoryMocks.RoomTemplateRepository.MockRoomTemplateSaveCrudRepository;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import Utils.InterfaceAdapter;
import com.google.gson.GsonBuilder;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Managers.AddRoomTemplateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.*;

@Service
public class AddRoomTemplateTestsAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    protected AddRoomTemplateManager addRoomTemplateManager;

    protected DataGenerator dataGenerator;

    protected String apiKey;

    protected Ram ram;

    //------------------------------set up-----------------------------------------------//

    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        GsonBuilder builderMission = new GsonBuilder();
        builderMission.registerTypeAdapter(Mission.class, new InterfaceAdapter());
        apiKey="key";
    }

    void setUpMocks(){
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        roomTemplateCrudRepository=new RoomTemplateCrudRepositoryMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        addRoomTemplateManager=new AddRoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    void setUpAddRoomTemplate(){
        setUpMocks();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
    }

    //------------------------------tests------------------------------------------------//

    @Test
    void testAddRoomTemplateValid(){
        setUpAddRoomTemplate();
        testAddRoomTemplateValidTest();
        tearDownAddRoomTemplate();
    }

    protected void testAddRoomTemplateValidTest(){
        Response<Boolean> response=addRoomTemplateManager.createRoomTemplate(apiKey,dataGenerator.getRoomTemplateData(Data.VALID));
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
    }

    @Test
    void testAddRoomTemplateInvalidNullName(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.NULL_NAME,OpCode.Wrong_Name);
        tearDownAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidEmptyName(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.EMPTY_NAME,OpCode.Wrong_Name);
        tearDownAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidAmountLessThenZero(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.NEG_AMOUNT,OpCode.Wrong_Amount);
        tearDownAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidAmountBiggerThenMissions(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.BIG_AMOUNT,OpCode.Too_Big_Amount);
        tearDownAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidNullType(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.NULL_TYPE,OpCode.Wrong_Type);
        tearDownAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidMissions(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.NULL_LIST,OpCode.Wrong_List);
        tearDownAddRoomTemplate();
    }

    @Test
    void testAddRoomTemplateInvalidEmptyMissions(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.EMPTY_LIST,OpCode.Wrong_List);
        tearDownAddRoomTemplate();
    }

    @Test
    void testMissionInvalidExceptionTeacherCrudRepository(){
        setUpAddRoomTemplate();
        addRoomTemplateManager=new AddRoomTemplateManager(ram,new TeacherCrudRepositoryMockExceptionFindById(),missionCrudRepository,roomTemplateCrudRepository);
        checkWrongAddRoomTemplate(Data.VALID,OpCode.DB_Error);
        tearDownAddRoomTemplate();
    }

    @Test
    void testMissionInvalidExceptionMissionCrudRepository(){
        setUpAddRoomTemplate();
        missionCrudRepository=new MissionCrudRepositoryMockExeptionFindById();
        addRoomTemplateManager=new AddRoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        checkWrongAddRoomTemplate(Data.VALID,OpCode.DB_Error);
        tearDownAddRoomTemplate();
    }

    @Test
    void testMissionInvalidExceptionRoomTemplateSaveCrudRepository(){
        setUpAddRoomTemplate();
        roomTemplateCrudRepository=new MockRoomTemplateSaveCrudRepository();
        addRoomTemplateManager=new AddRoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        checkWrongAddRoomTemplate(Data.VALID,OpCode.DB_Error);
        tearDownAddRoomTemplate();
    }

    protected void checkWrongAddRoomTemplate(Data data,OpCode opCode){
        Response<Boolean> response=addRoomTemplateManager.createRoomTemplate(apiKey,dataGenerator.getRoomTemplateData(data));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
    }

    //---------------------------------------tearDown-------------------------------------//

    protected void tearDownAddRoomTemplate() {
        teacherCrudRepository.deleteAll();
    }
}
