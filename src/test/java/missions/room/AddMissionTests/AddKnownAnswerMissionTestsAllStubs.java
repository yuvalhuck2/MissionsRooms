package missions.room.AddMissionTests;

import CrudRepositories.MissionCrudRepository;
import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMockExeptionSave;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMock;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import DomainMocks.MockRam;
import DomainMocks.MissionMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import missions.room.Managers.AddMissionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.Assert.*;

@Service
public class AddKnownAnswerMissionTestsAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

    @Autowired
    protected MissionCrudRepository missionCrudRepository;

    @Autowired
    protected AddMissionManager addMissionManager;

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
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }

    void setUpAddMission(){
        setUpMocks();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }

    //------------------------------tests------------------------------------------------//
    @Test
    void testAddMissionValid(){
        setUpAddMission();
        testAddMissionValidTest();
        tearDownAddMission();
    }

    protected void testAddMissionValidTest() {
        Response<Boolean> response= addMissionManager.addMission(apiKey,missionString);
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
        addMissionManager=new AddMissionManager(ram,new TeacherCrudRepositoryMockExceptionFindById(),missionCrudRepository);
        checkWrongAddMission(OpCode.DB_Error);
        tearDownAddMission();
    }

    @Test
    void testMissionInvalidExceptionMissionCrudRepository(){
        setUpAddMission();
        missionCrudRepository=new MissionCrudRepositoryMockExeptionSave();
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository,missionCrudRepository);
        checkWrongAddMission(OpCode.DB_Error);
        tearDownAddMission();
    }



    protected void checkWrongAddMission(OpCode opCode) {
        Response<Boolean> response= addMissionManager.addMission(apiKey,missionString);
        assertFalse(response.getValue());
        assertEquals(response.getReason(), opCode);
    }

    //---------------------------------------tearDown-------------------------------------//
    protected void tearDownAddMission() {
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.deleteAll();
    }

}
