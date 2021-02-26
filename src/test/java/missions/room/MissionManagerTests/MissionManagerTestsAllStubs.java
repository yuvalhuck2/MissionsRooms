package missions.room.MissionManagerTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.MissionData;
import DataAPI.OpCode;
import DataAPI.Response;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock2TypesMission;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMockExeptionSave;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMockNoMissions;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockNotExist;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.Ram;
import DomainMocks.MockRam;
import DomainMocks.MissionMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import missions.room.Domain.missions.KnownAnswerMission;
import missions.room.Managers.MissionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.Assert.*;

@Service
public class MissionManagerTestsAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected MissionManager missionManager;

    protected DataGenerator dataGenerator;

    protected Ram ram;

    protected String missionString;

    protected Gson gson;

    protected String apiKey;

    //------------------------------set up-----------------------------------------------//
    @BeforeEach
    void setUp() {
        dataGenerator=new DataGenerator();
        GsonBuilder builderMission = new GsonBuilder();
        builderMission.registerTypeAdapter(Mission.class, new InterfaceAdapter());
        gson = builderMission.create();
        apiKey="key";
    }

    void setUpMocks(){
        teacherCrudRepository=new TeacherCrudRepositoryMock(dataGenerator);
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        missionString=gson.toJson(new MissionMock(),Mission.class);
        missionManager =new MissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }

    void setUpAddMission(){
        setUpMocks();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }

    void setupSearch(){
        setUpMocks();
    }



    //------------------------------tests------------------------------------------------//
    @Test
    void testAddMissionValid(){
        setUpAddMission();
        testAddMissionValidTest();
        tearDownAddMission();
    }

    protected void testAddMissionValidTest() {
        Response<Boolean> response= missionManager.addMission(apiKey,missionString);
        assertTrue(response.getValue());
        assertEquals(response.getReason(), OpCode.Success);
    }

    @Test
    void testMissionInvalidNullMission(){
        setUpAddMission();
        missionString=gson.toJson(null,Mission.class);
        checkWrongAddMission(OpCode.Null_Error);
        tearDownAddMission();
    }

    @Test
    void testMissionInvalidExceptionMission(){
        setUpAddMission();
        missionString="hello";
        checkWrongAddMission(OpCode.Not_Mission);
        tearDownAddMission();
    }

    @Test
    void testMissionInvalidExceptionTeacherCrudRepository(){
        setUpAddMission();
        missionManager =new MissionManager(ram,new TeacherCrudRepositoryMockExceptionFindById(),missionCrudRepository);
        checkWrongAddMission(OpCode.DB_Error);
        tearDownAddMission();
    }

    @Test
    void testMissionInvalidExceptionMissionCrudRepository(){
        setUpAddMission();
        missionCrudRepository=new MissionCrudRepositoryMockExeptionSave();
        missionManager =new MissionManager(ram,teacherCrudRepository,missionCrudRepository);
        checkWrongAddMission(OpCode.DB_Error);
        tearDownAddMission();
    }



    protected void checkWrongAddMission(OpCode opCode) {
        Response<Boolean> response= missionManager.addMission(apiKey,missionString);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
    }


    @Test
    void testSearchMissionsValid(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        testSearchMissionsValidTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));
    }

    protected void testSearchMissionsValidTest(){
        Response<List<MissionData>> response=missionManager.searchMissions(apiKey);
        assertEquals(response.getReason(),OpCode.Success);
        assertNotNull(response.getValue());
        assertEquals(response.getValue().size(),1);
        Mission mission=dataGenerator.getMission(Data.Valid_Deterministic);
        MissionData missionData=response.getValue().get(0);
        assertEquals(mission.getMissionId(),missionData.getMissionId());
        assertEquals(mission.getMissionTypes(),missionData.getMissionTypes());
        assertEquals(((KnownAnswerMission)mission).getQuestion(),missionData.getQuestion().get(0));
        assertEquals(missionData.getTimeForAns(),-1);
    }

    @Test
    void testSearchMissionsNull(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionManager =new MissionManager(ram,teacherCrudRepository,new MissionCrudRepositoryMockNoMissions(dataGenerator));
        testSearchMissionsNullTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }

    protected void testSearchMissionsNullTest(){
        Response<List<MissionData>> response=missionManager.searchMissions(apiKey);
        assertEquals(response.getReason(),OpCode.Success);
        assertEquals(response.getValue().size(),0);
    }

    @Test
    void testSearchTwoMissionsDiffTypes(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.VALID_STORY));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        missionManager=new MissionManager(ram,teacherCrudRepository,new MissionCrudRepositoryMock2TypesMission(dataGenerator));
        testSearchTwoMissionsDiffTypesTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.delete(dataGenerator.getMission(Data.VALID_STORY));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));
    }

    protected void testSearchTwoMissionsDiffTypesTest(){
        Response<List<MissionData>> response=missionManager.searchMissions(apiKey);
        assertEquals(response.getReason(),OpCode.Success);
        assertNotNull(response.getValue());
        assertEquals(response.getValue().size(),2);
    }


    @Test
    void testSearchMissionsTeacherNotFoundError(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.WRONG_NAME));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.WRONG_NAME).getAlias());
        missionManager =new MissionManager(ram,new TeacherCrudRepositoryMockNotExist(dataGenerator),missionCrudRepository);
        testSearchMissionsTeacherNotFoundErrorTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.WRONG_NAME));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));

    }
    protected void testSearchMissionsTeacherNotFoundErrorTest(){

        Response<List<MissionData>> response=missionManager.searchMissions(apiKey);
        assertEquals(response.getReason(),OpCode.Not_Exist);
        assertNull(response.getValue());

    }


    //---------------------------------------tearDown-------------------------------------//
    protected void tearDownAddMission() {
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.deleteAll();
    }

}
