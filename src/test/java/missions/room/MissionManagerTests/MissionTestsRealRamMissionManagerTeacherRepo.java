package missions.room.MissionManagerTests;

import Data.Data;
import DataAPI.OpCode;
import RepositoryMocks.MissionRepository.MissionCrudRepositoryMock;
import RepositoryMocks.TeacherRepository.TeacherCrudRepositoryMockExceptionFindById;
import missions.room.Domain.Mission;
import missions.room.Domain.Ram;
import missions.room.Managers.MissionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = {"classpath:application-unit-integration-tests.properties"})
public class MissionTestsRealRamMissionManagerTeacherRepo extends MissionTestsRealRamMissionManager {

    @Override
    void setUpMocks() {
        missionCrudRepository=new MissionCrudRepositoryMock(dataGenerator);
        missionString=gson.toJson(dataGenerator.getMission(Data.Valid_Deterministic), Mission.class);
        ram=new Ram();
        missionManager =new MissionManager(ram,teacherCrudRepository,missionCrudRepository);
    }


    @Test
    void testMissionInvalidWrongTeacher(){
        setUpAddMission();
        ram.addApi(apiKey,dataGenerator.getTeacher(Data.VALID_WITH_PASSWORD).getPassword());
        checkWrongAddMission(OpCode.Not_Exist);
        tearDownAddMission();
    }

    @Test
    @Override
    void testSearchMissionsTeacherNotFoundError(){
        setupSearch();
        teacherCrudRepository.save(dataGenerator.getTeacher(Data.WRONG_NAME));
        missionCrudRepository.save(dataGenerator.getMission(Data.Valid_Deterministic));
        testSearchMissionsTeacherNotFoundErrorTest();
        teacherCrudRepository.delete(dataGenerator.getTeacher(Data.WRONG_NAME));
        missionCrudRepository.delete(dataGenerator.getMission(Data.Valid_Deterministic));

    }

}
