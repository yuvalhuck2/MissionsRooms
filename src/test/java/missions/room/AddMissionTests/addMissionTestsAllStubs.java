package missions.room.AddMissionTests;

import CrudRepositories.TeacherCrudRepository;
import Data.Data;
import Data.DataGenerator;
import DataAPI.OpCode;
import DataAPI.Response;
import Domain.Mission;
import Domain.Ram;
import DomainMocks.MockRam;
import DomainMocks.MissionMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockTeacherMock;
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
public class addMissionTestsAllStubs {

    @Autowired
    protected TeacherCrudRepository teacherCrudRepository;

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
        teacherCrudRepository=new TeacherCrudRepositoryMockTeacherMock(dataGenerator);
        ram=new MockRam(dataGenerator);
        missionString=gson.toJson(new MissionMock(),Mission.class);
        addMissionManager=new AddMissionManager(ram,teacherCrudRepository);
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
        addMissionManager=new AddMissionManager(ram,new TeacherCrudRepositoryMockExceptionFindById());
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
    }

}
