package missions.room.RoomTemplateTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.RoomTemplateCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import DataAPI.RoomTemplateForSearch;
import DomainMocks.MockRam;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMockExeptionFindById;
import RepositoryMocks.RoomTemplateRepository.MockRoomTemplateSaveCrudRepository;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateCrudRepositoryMock;
import RepositoryMocks.RoomTemplateRepository.RoomTemplateMockRepositorySearch;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockNotExist;
import Utils.InterfaceAdapter;
import com.google.gson.GsonBuilder;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.RoomTemplate;
import missions.room.Managers.RoomTemplateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.Assert.*;

@Service
public class RoomTemplateManagerTestsAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected RoomTemplateCrudRepository roomTemplateCrudRepository;

    @Autowired
    protected RoomTemplateManager roomTemplateManager;

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
        roomTemplateManager =new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
    }

    void setUpAddRoomTemplate(){
        setUpMocks();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
    }

    void setupSearchRoomTemplate(){
        setUpMocks();
    }

    //------------------------------tests------------------------------------------------//

    @Test
    void testAddRoomTemplateValid(){
        setUpAddRoomTemplate();
        testAddRoomTemplateValidTest();
        tearDownAddRoomTemplate();
    }

    protected void testAddRoomTemplateValidTest(){
        Response<Boolean> response= roomTemplateManager.createRoomTemplate(apiKey,dataGenerator.getRoomTemplateData(Data.VALID));
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
    void testAddRoomTemplateInvalidNotMatchMissionType(){
        setUpAddRoomTemplate();
        checkWrongAddRoomTemplate(Data.TYPE_NOT_MATCH,OpCode.Type_Not_Match);
        tearDownAddRoomTemplate();
    }

    @Test
    void testMissionInvalidExceptionTeacherCrudRepository(){
        setUpAddRoomTemplate();
        roomTemplateManager =new RoomTemplateManager(ram,new TeacherCrudRepositoryMockExceptionFindById(),missionCrudRepository,roomTemplateCrudRepository);
        checkWrongAddRoomTemplate(Data.VALID,OpCode.DB_Error);
        tearDownAddRoomTemplate();
    }

    @Test
    void testMissionInvalidExceptionMissionCrudRepository(){
        setUpAddRoomTemplate();
        missionCrudRepository=new MissionCrudRepositoryMockExeptionFindById();
        roomTemplateManager =new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        checkWrongAddRoomTemplate(Data.VALID,OpCode.DB_Error);
        tearDownAddRoomTemplate();
    }

    @Test
    void testMissionInvalidExceptionRoomTemplateSaveCrudRepository(){
        setUpAddRoomTemplate();
        roomTemplateCrudRepository=new MockRoomTemplateSaveCrudRepository();
        roomTemplateManager =new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        checkWrongAddRoomTemplate(Data.VALID,OpCode.DB_Error);
        tearDownAddRoomTemplate();
    }

    protected void checkWrongAddRoomTemplate(Data data,OpCode opCode){
        Response<Boolean> response= roomTemplateManager.createRoomTemplate(apiKey,dataGenerator.getRoomTemplateData(data));
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
    }

    @Test
    void testSearchRoomTemplateValid(){
        setupSearchRoomTemplate();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));
        roomTemplateCrudRepository=new RoomTemplateMockRepositorySearch(dataGenerator,Data.VALID);
        roomTemplateManager=new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        testSearchRoomTemplateValidTest();
        teardownSearchTemplate();
        roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.VALID));
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));

    }

    protected void testSearchRoomTemplateValidTest(){
        Response<List<RoomTemplateForSearch>>response=roomTemplateManager.searchRoomTemplates(apiKey);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(),1);
        RoomTemplateForSearch elm=response.getValue().get(0);
        RoomTemplate roomTemplate=dataGenerator.getRoomTemplate(Data.VALID);
        assertEquals(elm.getName(),roomTemplate.getName());
        assertEquals(elm.getMinimalMissionsToPass(),roomTemplate.getMinimalMissionsToPass());
        assertEquals(elm.getMissions().size(),roomTemplate.getMissions().size());
        assertEquals(elm.getType(),roomTemplate.getType());
        assertEquals(elm.getId(),roomTemplate.getRoomTemplateId());

    }

    @Test
    void testSearchRoomTemplateEmpty(){
        setupSearchRoomTemplate();

        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        roomTemplateCrudRepository=new RoomTemplateMockRepositorySearch(dataGenerator,Data.EMPTY);
        roomTemplateManager=new RoomTemplateManager(ram,teacherCrudRepository,missionCrudRepository,roomTemplateCrudRepository);
        testSearchRoomTemplateEmptyTest();
        teardownSearchTemplate();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));

    }

    protected void testSearchRoomTemplateEmptyTest(){
        Response<List<RoomTemplateForSearch>>response=roomTemplateManager.searchRoomTemplates(apiKey);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(),0);
    }

    @Test
    void testSearchRoomTemplateInvalidTeacher(){
        setupSearchRoomTemplate();

        roomTemplateCrudRepository=new RoomTemplateMockRepositorySearch(dataGenerator,Data.VALID);
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.WRONG_NAME));
        roomTemplateCrudRepository.save(dataGenerator.getRoomTemplate(Data.VALID));

        ram.addApi(apiKey,dataGenerator.getTeacher(Data.WRONG_NAME).getAlias());
        roomTemplateManager=new RoomTemplateManager(ram,new TeacherCrudRepositoryMockNotExist(dataGenerator),missionCrudRepository,roomTemplateCrudRepository);
        testSearchRoomTemplateInvalidTeacherTest();
        teardownSearchTemplate();
        roomTemplateCrudRepository.delete(dataGenerator.getRoomTemplate(Data.VALID));
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.WRONG_NAME));

    }

    protected void testSearchRoomTemplateInvalidTeacherTest(){
        Response<List<RoomTemplateForSearch>>response=roomTemplateManager.searchRoomTemplates(apiKey);
        assertEquals(response.getReason(),OpCode.Not_Exist);
    }



    //---------------------------------------tearDown-------------------------------------//

    protected void tearDownAddRoomTemplate() {
        teacherCrudRepository.deleteAll();
    }

    protected void teardownSearchTemplate(){}
}
