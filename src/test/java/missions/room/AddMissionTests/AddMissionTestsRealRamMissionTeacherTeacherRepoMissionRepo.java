package missions.room.AddMissionTests;

import CrudRepositories.MissionCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import DataAPI.Response;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.missions.KnownAnswerMission;
import missions.room.Managers.AddMissionManager;
import missions.room.Repo.MissionRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class AddMissionTestsRealRamMissionTeacherTeacherRepoMissionRepo extends AddMissionTestsRealRamMissionTeacherRepo {

    @Autowired
    private MissionCrudRepository missionCrudRepositoryNotMock;

    @Override
    void setUpMocks() {
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
    }

    @Override
    void setUpAddMission(){
        try {
            Field managerRam = AddMissionManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(addMissionManager);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.setUpAddMission();
    }

    @Override
    protected void testAddMissionValidTest() {
        super.testAddMissionValidTest();
        //check the new mission added to the db
        KnownAnswerMission dataMission= (KnownAnswerMission) dataGenerator.getMission(Data.Valid_Deterministic);
        KnownAnswerMission mission= (KnownAnswerMission) missionCrudRepositoryNotMock.findAll().iterator().next();
        assertEquals(mission.getMissionTypes().iterator().next(),dataMission.getMissionTypes().iterator().next());
        assertEquals(mission.getQuestion(),dataMission.getQuestion());
        assertEquals(mission.getRealAnswer(),dataMission.getRealAnswer());
    }


    @Override
    protected void checkWrongAddMission(OpCode opCode) {
        super.checkWrongAddMission(opCode);
        //check no new mission added to the db
        assertFalse(missionCrudRepositoryNotMock.findAll().iterator().hasNext());
    }
}
