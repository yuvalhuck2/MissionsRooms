package missions.room.MissionManagerTests;

import CrudRepositories.MissionCrudRepository;
import Data.Data;
import DataAPI.OpCode;
import missions.room.Domain.missions.Mission;
import missions.room.Domain.Ram;
import missions.room.Domain.missions.KnownAnswerMission;
import missions.room.Managers.TeacherManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class MissionTestsRealRamMissionTeacherTeacherRepoMissionManagerRepo extends MissionTestsRealRamMissionManagerTeacherRepo {

    @Autowired
    private MissionCrudRepository missionCrudRepositoryNotMock;

    @Override
    void setUpMocks() {
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
    }

    @Override
    void setUpAddMission(){
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(missionManager);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.setUpAddMission();
    }

    @Override
    void setupSearch(){
        try {
            Field managerRam = TeacherManager.class.getDeclaredField("ram");
            managerRam.setAccessible(true);
            ram=(Ram)managerRam.get(missionManager);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            fail();
        }
        super.setupSearch();

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



    @Test
    @Override
    void testSearchTwoMissionsDiffTypes(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.save(dataGenerator.getMission(Data.VALID_STORY));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        testSearchTwoMissionsDiffTypesTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        missionCrudRepository.delete(dataGenerator.getMission(Data.VALID_STORY));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));
    }

    @Test
    @Override
    void testSearchMissionsNull(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
        testSearchMissionsNullTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD));
    }


}
